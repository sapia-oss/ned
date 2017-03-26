package org.sapia.ned.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Provides serialization-related methods.
 * 
 * @author yduchesne
 *
 */
public final class Serialization {

  private static final int DEFAULT_BUFSZ = 1024;
  
  private Serialization() {
    
  }
  
  /**
   * @param payload an array of bytes corresponding to the payload of the object to deserialize.
   * @return instance resulting from deserialization.
   */
  @SuppressWarnings("unchecked")
  public static <T> T deserialize(byte[] payload) {
    try {
      ByteArrayInputStream bis = new ByteArrayInputStream(payload);
      ObjectInput          ois = new ObjectInputStream(bis);
      return (T) ois.readObject();
    } catch (Exception e) {
      throw new IllegalStateException("Could not deserialize payload", e);
    }
  }

  /**
   * @param serializable a {@link Serializable} instance.
   * @return the bytes resulting from serialization of the given object.
   */
  public static byte[] serialize(Serializable serializable) {
    return serialize(serializable, DEFAULT_BUFSZ);
  }
  
  /**
   * @param serializable a {@link Serializable} instance.
   * @param bufSize the size of the buffer that is used internally to perform serialization.
   * @return the bytes resulting from serialization of the given object.
   */
  public static byte[] serialize(Serializable serializable, int bufSize) {
    try {
      ByteArrayOutputStream bos = new ByteArrayOutputStream(bufSize);    
      ObjectOutputStream    oos = new ObjectOutputStream(bos);
      oos.writeObject(serializable);
      oos.flush();
      oos.close();
      return bos.toByteArray();
    } catch (Exception e) {
      throw new IllegalStateException("Could not serialize object", e);
    }
  }
}
