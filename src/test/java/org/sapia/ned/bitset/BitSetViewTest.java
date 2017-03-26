package org.sapia.ned.bitset;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class BitSetViewTest {
  
  private ImmutableBitSet delegate;

  @Before
  public void setUp() throws Exception {
    // 5432 1098 7654 3210
    // 0011 0110 1010 1111
    delegate = new BooleanBitSet("0011011010101111");
  }

  @Test
  public void testGetBitAt_from_start() {
    ImmutableBitSet view = getView(0, 8);
    
    assertThat(view.getBitAt(0)).isTrue();
    assertThat(view.getBitAt(1)).isTrue();
    assertThat(view.getBitAt(2)).isTrue();
    assertThat(view.getBitAt(3)).isTrue();
    assertThat(view.getBitAt(4)).isFalse();
    assertThat(view.getBitAt(5)).isTrue();
    assertThat(view.getBitAt(6)).isFalse();
    assertThat(view.getBitAt(7)).isTrue();
  }
  
  @Test
  public void testSize_from_start() {
    ImmutableBitSet view = getView(0, 8);
    
    assertThat(view.size()).isEqualTo(8);
  }
  
  @Test
  public void testGetBitAt_from_middle() {
    ImmutableBitSet view = getView(8, 8);

    assertThat(view.getBitAt(0)).isFalse();
    assertThat(view.getBitAt(1)).isTrue();
    assertThat(view.getBitAt(2)).isTrue();
    assertThat(view.getBitAt(3)).isFalse();
    assertThat(view.getBitAt(4)).isTrue();
    assertThat(view.getBitAt(5)).isTrue();
    assertThat(view.getBitAt(6)).isFalse();
    assertThat(view.getBitAt(7)).isFalse();
  }
  
  @Test
  public void testSize_from_middle() {
    ImmutableBitSet view = getView(8, 8);
    
    assertThat(view.size()).isEqualTo(8);
  }
  
  @Test
  public void testGetBitAt_forSlice() {
    ImmutableBitSet view = getView(4, 8);

    assertThat(view.getBitAt(0)).isFalse();
    assertThat(view.getBitAt(1)).isTrue();
    assertThat(view.getBitAt(2)).isFalse();
    assertThat(view.getBitAt(3)).isTrue();
    assertThat(view.getBitAt(4)).isFalse();
    assertThat(view.getBitAt(5)).isTrue();
    assertThat(view.getBitAt(6)).isTrue();
    assertThat(view.getBitAt(7)).isFalse();
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNew_negative_offset() {
    getView(-1, 8);
  }
  
  
  @Test(expected = IllegalArgumentException.class)
  public void testNew_negative_size() {
    getView(0, -1);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNew_offset_too_large() {
    getView(17, 8);
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void testNew_size_too_large() {
    getView(9, 8);
  }

  private ImmutableBitSet getView(int offset, int size) {
    return new BitSetView(delegate, offset, size);
  }
}
