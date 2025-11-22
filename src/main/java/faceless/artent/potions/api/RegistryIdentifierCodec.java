package faceless.artent.potions.api;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.util.Identifier;

import java.util.Optional;

public final class RegistryIdentifierCodec<E extends ObjectWithIdentifier>
    implements Codec<E> {
  private final RegistryKey<? extends Registry<E>> registryRef;
  private final Codec<E> elementCodec;
  private final boolean allowInlineDefinitions;

  public static <E extends ObjectWithIdentifier> RegistryIdentifierCodec<E> of(
      RegistryKey<? extends Registry<E>> registryRef,
      Codec<E> elementCodec) {
    return RegistryIdentifierCodec.of(registryRef, elementCodec, true);
  }

  public static <E extends ObjectWithIdentifier> RegistryIdentifierCodec<E> of(
      RegistryKey<? extends Registry<E>> registryRef,
      Codec<E> elementCodec,
      boolean allowInlineDefinitions) {
    return new RegistryIdentifierCodec<E>(registryRef, elementCodec, allowInlineDefinitions);
  }

  private RegistryIdentifierCodec(
      RegistryKey<? extends Registry<E>> registryRef,
      Codec<E> elementCodec,
      boolean allowInlineDefinitions) {
    this.registryRef = registryRef;
    this.elementCodec = elementCodec;
    this.allowInlineDefinitions = allowInlineDefinitions;
  }

  @Override
  public <T> DataResult<T> encode(E registryEntry, DynamicOps<T> dynamicOps, T object) {
    RegistryOps<T> registryOps = (RegistryOps<T>) dynamicOps;
    Optional<RegistryEntryOwner<E>> optional = registryOps.getOwner(this.registryRef);
    if (dynamicOps instanceof RegistryOps && optional.isPresent()) {
      return Identifier.CODEC.encode(
          registryEntry
              .getIdentifier(), dynamicOps, object);
    }
    return this.elementCodec.encode(registryEntry, dynamicOps, object);
  }

  @Override
  public <T> DataResult<Pair<E, T>> decode(DynamicOps<T> ops, T input) {
    if (ops instanceof RegistryOps<T> registryOps) {
      Optional<RegistryEntryLookup<E>> optional = registryOps.getEntryLookup(this.registryRef);
      if (optional.isEmpty()) {
        return DataResult.error(() -> "Registry does not exist: " + this.registryRef);
      }
      RegistryEntryLookup<E> registryEntryLookup = optional.get();
      DataResult<Pair<Identifier, T>> dataResult = Identifier.CODEC.decode(ops, input);
      if (dataResult.result().isEmpty()) {
        if (!this.allowInlineDefinitions) {
          return DataResult.error(() -> "Inline definitions not allowed here");
        }
        return this.elementCodec.decode(ops, input);
      }
      Pair<Identifier, T> pair2 = dataResult.result().get();
      RegistryKey<E> registryKey = RegistryKey.of(this.registryRef, pair2.getFirst());
      return registryEntryLookup
          .getOptional(registryKey)
          .map(RegistryEntry.Reference::value)
          .map(DataResult::success)
          .orElseGet(() -> DataResult.error(() -> "Failed to get element " + registryKey))
          .map((E reference) -> Pair.of(reference, pair2.getSecond())).setLifecycle(
              Lifecycle.stable());
    }
    return this.elementCodec.decode(ops, input);
  }

  public String toString() {
    return "RegistryFileCodec[" + this.registryRef + " " + this.elementCodec + "]";
  }
}


