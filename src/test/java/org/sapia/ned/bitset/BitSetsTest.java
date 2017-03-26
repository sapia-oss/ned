package org.sapia.ned.bitset;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.BitSet;

public class BitSetsTest {

  @Test
  public void testView() {
    assertThat(BitSets.view(new ByteBitSet((byte) 0b11111111), 4, 4)).isInstanceOf(BitSetView.class);
  }

  @Test
  public void testValueOf_byte_literal() {
    assertThat(BitSets.valueOf("11111111")).isInstanceOf(ByteBitSet.class);
  }
  
  @Test
  public void testValueOf_multi_byte_literal() {
    assertThat(BitSets.valueOf("000011110000111100001111")).isInstanceOf(MultiByteBitset.class);
  }

  @Test
  public void testValueOf_binary_literal() {
    assertThat(BitSets.valueOf("0000111101")).isInstanceOf(BooleanBitSet.class);
  }
  
  @Test
  public void testValueOf_bitset() {
    BitSet          mutable = new BitSet(8);
    mutable.set(0, false);
    mutable.set(1, true);
    mutable.set(2, true);
    mutable.set(3, false);
    mutable.set(4, true);
    mutable.set(5, false);
    mutable.set(6, true);
    mutable.set(7, false);
    
    ImmutableBitSet copy    = BitSets.valueOf(mutable);
    
    assertThat(copy.getBitAt(0)).isFalse();
    assertThat(copy.getBitAt(1)).isTrue();
    assertThat(copy.getBitAt(2)).isTrue();
    assertThat(copy.getBitAt(3)).isFalse();
    assertThat(copy.getBitAt(4)).isTrue();
    assertThat(copy.getBitAt(5)).isFalse();
    assertThat(copy.getBitAt(6)).isTrue();
    assertThat(copy.getBitAt(7)).isFalse();
  }

}
