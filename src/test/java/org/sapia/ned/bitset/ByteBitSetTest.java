package org.sapia.ned.bitset;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class ByteBitSetTest {

  private ByteBitSet bitset;
  
  @Before
  public void setUp() throws Exception {
    bitset = new ByteBitSet((byte) 0b00110011);
  }

  @Test
  public void testToString() {
    assertThat(bitset.toString()).isEqualTo("00110011");
  }

  @Test
  public void testGetBitAt() {
    assertThat(bitset.getBitAt(0)).isTrue();
    assertThat(bitset.getBitAt(1)).isTrue();
    assertThat(bitset.getBitAt(2)).isFalse();
    assertThat(bitset.getBitAt(3)).isFalse();
    assertThat(bitset.getBitAt(4)).isTrue();
    assertThat(bitset.getBitAt(5)).isTrue();
    assertThat(bitset.getBitAt(6)).isFalse();
    assertThat(bitset.getBitAt(7)).isFalse();
  }

  @Test
  public void testSize() {
    assertThat(bitset.size()).isEqualTo(8);
  }

}
