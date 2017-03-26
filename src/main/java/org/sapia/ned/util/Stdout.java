package org.sapia.ned.util;

/**
 * Utility class to print to the console.
 * 
 * @author yduchese 
 */
public final class Stdout {

  private Stdout() {
    
  }
  
  /**
   * @param msg a message to output.
   * @param args the message's argument.
   */
  public static void msg(String msg, Object...args) {
    System.out.println(String.format(msg, args));
  }
  
  /**
   * @param args the arguments to output.
   */
  public static void args(Object...args) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < args.length; i++) {
      sb.append(args[i]);
    }
    System.out.println(sb.toString());
  }
}
