package org.sapia.ned.bitset;

import org.junit.Before;
import org.junit.Test;
import org.sapia.ned.bitset.BooleanBitSet;

import static org.assertj.core.api.Assertions.*;

public class BooleanBitSetTest {
  
  private BooleanBitSet set;

  @Before
  public void setUp() throws Exception {
    set = new BooleanBitSet("10110110");
  }

  @Test
  public void testGetBitAt() {
    assertThat(set.getBitAt(0)).isFalse();
    assertThat(set.getBitAt(1)).isTrue();
    assertThat(set.getBitAt(2)).isTrue();
    assertThat(set.getBitAt(3)).isFalse();
    assertThat(set.getBitAt(4)).isTrue();
    assertThat(set.getBitAt(5)).isTrue();
    assertThat(set.getBitAt(6)).isFalse();
    assertThat(set.getBitAt(7)).isTrue();
  }

  @Test
  public void testSize() {
    assertThat(set.size()).isEqualTo(8);
  }

}
