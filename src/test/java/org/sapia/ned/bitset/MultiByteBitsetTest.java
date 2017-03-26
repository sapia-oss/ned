package org.sapia.ned.bitset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;
import org.sapia.ned.bitset.MultiByteBitset;

public class MultiByteBitsetTest {
  
  private MultiByteBitset singleByte;
  private MultiByteBitset multiBytes;

  @Before
  public void setUp() throws Exception {
    singleByte = new MultiByteBitset(new byte[] { (byte) 0b01101101 });
    multiBytes = new MultiByteBitset(new byte[] { (byte) 0b01101101, (byte) 0b10110110 });
  }

  @Test
  public void testGetBitAt_single_byte() {
    assertThat(singleByte.getBitAt(0)).isTrue();
    assertThat(singleByte.getBitAt(3)).isTrue();
    assertThat(singleByte.getBitAt(7)).isFalse();
  }
  
  @Test
  public void testGetBitAt_multi_bytes() {
    assertThat(multiBytes.getBitAt(0)).isFalse();
    assertThat(multiBytes.getBitAt(2)).isTrue();
    assertThat(multiBytes.getBitAt(3)).isFalse();
    assertThat(multiBytes.getBitAt(7)).isTrue();

    assertThat(multiBytes.getBitAt(8)).isTrue();
    assertThat(multiBytes.getBitAt(11)).isTrue();
    assertThat(multiBytes.getBitAt(12)).isFalse();
    assertThat(multiBytes.getBitAt(15)).isFalse();
  }

  @Test
  public void testSize_single_byte() {
    assertThat(singleByte.size()).isEqualTo(8);
  }

  @Test
  public void testSize_multiple_bytes() {
    assertThat(multiBytes.size()).isEqualTo(16);
  }
  
  @Test
  public void testToMutable_single_byte() {
    BitSet bits = singleByte.toMutable();
    
    assertThat(bits.get(0)).isTrue();
    assertThat(bits.get(3)).isTrue();
    assertThat(bits.get(7)).isFalse();
  }
  
  @Test
  public void testToMutable_multi_bytes() {
    BitSet bits = multiBytes.toMutable();
    
    assertThat(bits.get(0)).isFalse();
    assertThat(bits.get(2)).isTrue();
    assertThat(bits.get(3)).isFalse();
    assertThat(bits.get(7)).isTrue();
    

    assertThat(bits.get(8)).isTrue();
    assertThat(bits.get(11)).isTrue();
    assertThat(bits.get(12)).isFalse();
    assertThat(bits.get(15)).isFalse();
  }

  @Test
  public void testToString_single_byte() {
    assertThat(singleByte.toString()).isEqualTo("01101101");
  }

  @Test
  public void testToString_multi_bytes() {
    assertThat(multiBytes.toString()).isEqualTo("0110110110110110");
  }
}
