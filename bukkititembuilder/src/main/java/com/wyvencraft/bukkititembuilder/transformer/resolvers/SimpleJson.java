package com.wyvencraft.bukkititembuilder.transformer.resolvers;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONObject;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
import com.wyvencraft.bukkititembuilder.transformer.TransformResolver;
import com.wyvencraft.bukkititembuilder.transformer.declarations.FieldDeclaration;
import com.wyvencraft.bukkititembuilder.transformer.declarations.GenericDeclaration;
import com.wyvencraft.bukkititembuilder.transformer.declarations.TransformedObjectDeclaration;
import com.wyvencraft.bukkititembuilder.transformer.exceptions.TransformException;
import com.wyvencraft.bukkititembuilder.transformer.postprocessor.PostProcessor;

/**
 * a class that represents Gson file configuration.
 */
@RequiredArgsConstructor
public class SimpleJson extends TransformResolver {

  /**
   * the container factory.
   */
  private static final ContainerFactory CONTAINER_FACTORY = new ContainerFactory() {

    @Override
    public Map<?, ?> createObjectContainer() {
      return new LinkedHashMap<>();
    }

    @Override
    public List<?> creatArrayContainer() {
      return new ArrayList<>();
    }
  };

  /**
   * the parser.
   */
  @NotNull
  private final JSONParser parser;

  /**
   * the cache map.
   */
  private Map<String, Object> map = new LinkedHashMap<>();

  /**
   * ctor.
   */
  public SimpleJson() {
    this(new JSONParser());
  }

  @NotNull
  @Override
  public List<String> getAllKeys() {
    return List.copyOf(this.map.keySet());
  }

  @NotNull
  @Override
  public Optional<Object> getValue(@NotNull final String path) {
    return Optional.ofNullable(this.map.get(path));
  }

  @Override
  public void load(@NotNull final InputStream inputStream, @NotNull final TransformedObjectDeclaration declaration)
    throws Exception {
    //noinspection unchecked
    this.map = (Map<String, Object>) this.parser.parse(PostProcessor.of(inputStream).getContext(), SimpleJson.CONTAINER_FACTORY);
    if (this.map != null) {
      return;
    }
    this.map = new LinkedHashMap<>();
  }

  @Override
  public boolean pathExists(@NotNull final String path) {
    return this.map.containsKey(path);
  }

  @Override
  public void removeValue(@NotNull final String path, @Nullable final GenericDeclaration genericType,
                          @Nullable final FieldDeclaration field) {
    this.map.remove(path);
  }

  @Nullable
  @Override
  public Object serialize(@Nullable final Object value, @Nullable final GenericDeclaration genericType,
                          final boolean conservative) throws TransformException {
    if (value == null) {
      return null;
    }
    final var genericsDeclaration = GenericDeclaration.of(value);
    if (genericsDeclaration.getType() == char.class || genericsDeclaration.getType() == Character.class) {
      return super.serialize(value, genericType, false);
    }
    return super.serialize(value, genericType, conservative);
  }

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.map.put(path, this.serialize(value, genericType, true));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration) {
    PostProcessor.of(new JSONObject(this.map).toJSONString()).write(outputStream);
  }
}
