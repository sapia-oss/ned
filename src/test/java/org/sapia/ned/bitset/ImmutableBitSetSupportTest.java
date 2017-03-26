package org.sapia.ned.bitset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.BitSet;

import org.junit.Test;
import org.sapia.ned.util.Safe;

/**
 * This test used the {@link BooleanBitSet} class, which extends {@link ImmutableBitSetSupport}, to
 * test the latter. 
 *
 */
public class ImmutableBitSetSupportTest {

  @Test
  public void testEquals() {
    BooleanBitSet set1 = new BooleanBitSet("010101");
    BooleanBitSet set2 = new BooleanBitSet("010101");
    
    assertThat(set1).isEqualTo(set2);
  }

  @Test
  public void testEquals_with_different_content() {
    BooleanBitSet set1 = new BooleanBitSet("010101");
    BooleanBitSet set2 = new BooleanBitSet("010100");
    
    assertThat(set1).isNotEqualTo(set2);
  }
  
  @Test
  public void testEquals_with_different_size() {
    BooleanBitSet set1 = new BooleanBitSet("010101");
    BooleanBitSet set2 = new BooleanBitSet("01010");
    
    assertThat(set1).isNotEqualTo(set2);
  }
  
  @Test
  public void testHashCode() {
    BooleanBitSet set = new BooleanBitSet("01010");
    
    int expected = 
          0 * 2^0 * Safe.PRIME 
        + 1 * 2^1 * Safe.PRIME 
        + 0 * 2^2 * Safe.PRIME
        + 1 * 2^3 * Safe.PRIME
        + 0 * 2^4 * Safe.PRIME;
        
    assertThat(set.hashCode()).isEqualTo(expected);
  }
  
  @Test
  public void testToString() {
    BooleanBitSet set = new BooleanBitSet("010101");
    
    assertThat(set.toString()).isEqualTo("010101");
  }
  
  @Test
  public void testToMutable() {
    BooleanBitSet set = new BooleanBitSet("010101");
    BitSet mutable = set.toMutable();
    
    for (int i = 0; i < set.size(); i++) {
      assertThat(set.getBitAt(i)).isEqualTo(mutable.get(i));
    }
  }
  
  @Test
  public void testIsPrefixOf() {
    BooleanBitSet set1 = new BooleanBitSet("010101");
    BooleanBitSet set2 = new BooleanBitSet("0101010000");
    
    assertThat(set1.isPrefixOf(set2)).isTrue();
  }
  
  @Test
  public void testIsPrefixOf_equals() {
    BooleanBitSet set1 = new BooleanBitSet("010101");
    BooleanBitSet set2 = new BooleanBitSet("010101");
    
    assertThat(set1.isPrefixOf(set2)).isTrue();
  }

  @Test
  public void testIsPrefixOf_false() {
    BooleanBitSet set1 = new BooleanBitSet("010101");
    BooleanBitSet set2 = new BooleanBitSet("010111");
    
    assertThat(set1.isPrefixOf(set2)).isFalse();
  }
  
  @Test
  public void testAnd() {
    BooleanBitSet set1 = new BooleanBitSet("110101");
    BooleanBitSet set2 = new BooleanBitSet("010111");
    
    ImmutableBitSet and = set1.and(set2);
    
    assertThat(and.getBitAt(0)).isTrue();
    assertThat(and.getBitAt(1)).isFalse();
    assertThat(and.getBitAt(2)).isTrue();
    assertThat(and.getBitAt(3)).isFalse();
    assertThat(and.getBitAt(4)).isTrue();
    assertThat(and.getBitAt(5)).isFalse();
  }
  
  @Test
  public void testOr() {
    BooleanBitSet set1 = new BooleanBitSet("110101");
    BooleanBitSet set2 = new BooleanBitSet("010111");
    
    ImmutableBitSet and = set1.or(set2);
    
    assertThat(and.getBitAt(0)).isTrue();
    assertThat(and.getBitAt(1)).isTrue();
    assertThat(and.getBitAt(2)).isTrue();
    assertThat(and.getBitAt(3)).isFalse();
    assertThat(and.getBitAt(4)).isTrue();
    assertThat(and.getBitAt(5)).isTrue();
  }
  
  @Test
  public void testNot() {
    BooleanBitSet set = new BooleanBitSet("110101");
    
    ImmutableBitSet and = set.not();
    
    assertThat(and.getBitAt(0)).isFalse();
    assertThat(and.getBitAt(1)).isTrue();
    assertThat(and.getBitAt(2)).isFalse();
    assertThat(and.getBitAt(3)).isTrue();
    assertThat(and.getBitAt(4)).isFalse();
    assertThat(and.getBitAt(5)).isFalse();
  }
}
