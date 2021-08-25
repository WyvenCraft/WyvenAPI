package com.wyvencraft.bukkititembuilder.transformer;

import com.wyvencraft.bukkititembuilder.transformer.serializers.MongoCredentialsSerializer;
import com.wyvencraft.bukkititembuilder.transformer.transformers.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Set;
import java.util.function.Consumer;

/**
 * an interface to determine transform packs.
 */
public interface TransformPack extends Consumer<@NotNull TransformRegistry> {

    /**
     * the default serializers.
     */
    Collection<ObjectSerializer<?>> DEFAULT_SERIALIZERS = Set.of(
            new MongoCredentialsSerializer());

    /**
     * the default transformers.
     */
    Collection<Transformer<?, ?>> DEFAULT_TRANSFORMERS = Set.of(
            new TransformerObjectToString(),
            new TransformerStringToString(),
            new TransformerStringToAddress(),
            new TransformerStringToRpString(),
            new TransformerStringListToRpList());

    /**
     * the default transformers reversed to string.
     */
    Collection<Transformer<?, ?>> DEFAULT_TRANSFORMERS_REVERSED_TO_STRING = Set.of(
            new TransformerStringToBigDecimal(),
            new TransformerStringToBigInteger(),
            new TransformerStringToBoolean(),
            new TransformerStringToByte(),
            new TransformerStringToCharacter(),
            new TransformerStringToDouble(),
            new TransformerStringToFloat(),
            new TransformerStringToInteger(),
            new TransformerStringToLocale(),
            new TransformerStringToLong(),
            new TransformerStringToShort(),
            new TransformerStringToUniqueId());

    /**
     * the default transform pack.
     */
    TransformPack DEFAULT = TransformPack.create(registry -> registry
            .withTransformers(TransformPack.DEFAULT_TRANSFORMERS)
            .withTransformersReversedToString(TransformPack.DEFAULT_TRANSFORMERS_REVERSED_TO_STRING)
            .withSerializers(TransformPack.DEFAULT_SERIALIZERS));

    /**
     * creates a simple transform pack instance.
     *
     * @param consumer the consumer to create.
     * @return a newly created transform pack.
     */
    @NotNull
    static TransformPack create(@NotNull final Consumer<@NotNull TransformRegistry> consumer) {
        return new Impl(consumer);
    }

    /**
     * a simple implementation of {@link TransformPack}.
     */
    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    final class Impl implements TransformPack {

        /**
         * the delegation.
         */
        @NotNull
        @Delegate
        private final Consumer<@NotNull TransformRegistry> delegation;
    }
}
