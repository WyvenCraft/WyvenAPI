package com.wyvencraft.bukkititembuilder.transformer.resolvers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.wyvencraft.bukkititembuilder.transformer.TransformResolver;
import com.wyvencraft.bukkititembuilder.transformer.declarations.FieldDeclaration;
import com.wyvencraft.bukkititembuilder.transformer.declarations.GenericDeclaration;
import com.wyvencraft.bukkititembuilder.transformer.declarations.TransformedObjectDeclaration;
import com.wyvencraft.bukkititembuilder.transformer.postprocessor.PostProcessor;

/**
 * a class that represents yaml file configuration.
 */
public class JacksonYaml extends TransformResolver {

  /**
   * the mapper.
   */
  private static final ObjectMapper MAPPER = new YAMLMapper()
    .enable(SerializationFeature.INDENT_OUTPUT);

  /**
   * the map type.
   */
  private static final MapType MAP_TYPE = JacksonYaml.MAPPER.getTypeFactory().constructMapType(HashMap.class, String.class,
    Object.class);

  /**
   * the cache map.
   */
  private Map<String, Object> map = new HashMap<>();

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
    final var context = PostProcessor.of(inputStream).getContext();
    this.map = JacksonYaml.MAPPER.readValue(context.isEmpty() ? "{}" : context, JacksonYaml.MAP_TYPE);
    if (this.map == null) {
      this.map = new LinkedHashMap<>();
    }
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

  @Override
  public void setValue(@NotNull final String path, @Nullable final Object value,
                       @Nullable final GenericDeclaration genericType, @Nullable final FieldDeclaration field) {
    this.map.put(path, this.serialize(value, genericType, true));
  }

  @Override
  public void write(@NotNull final OutputStream outputStream, @NotNull final TransformedObjectDeclaration declaration)
    throws Exception {
    JacksonYaml.MAPPER.writeValue(outputStream, this.map);
  }
}
