package org.sapia.ned.bitset;

import java.util.BitSet;

import org.sapia.ned.util.Safe;
import org.sapia.ned.util.logic.AndBitSet;
import org.sapia.ned.util.logic.NotBitSet;
import org.sapia.ned.util.logic.OrBitSet;

/**
 * Abstract support class for implementing {@link ImmutableBitSet}s.
 * 
 * @author yduchesne
 *
 */
public abstract class ImmutableBitSetSupport implements ImmutableBitSet {
  
  // --------------------------------------------------------------------------
  // ImmutableBitSet interface
  
  @Override
  public BitSet toMutable() {
    BitSet mutable = new BitSet(size());
    for (int i = 0; i < size(); i++) {
      mutable.set(i, getBitAt(i));
    }
    return mutable;
  }
   
  @Override
  public boolean isPrefixOf(ImmutableBitSet other) {
    if (size() <= other.size()) {
      int j = other.size() - 1;
      for (int i = size() - 1; i >= 0; i--) {
        if (getBitAt(i) != other.getBitAt(j--)) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
  
  @Override
  public ImmutableBitSet and(ImmutableBitSet other) {
    return new AndBitSet(this, other);
  }
  
  @Override
  public ImmutableBitSet or(ImmutableBitSet other) {
    return new OrBitSet(this, other);
  }
  
  @Override
  public ImmutableBitSet not() {
    return new NotBitSet(this);
  }

  // --------------------------------------------------------------------------
  // Object overrides
  
  @Override
  public String toString() {
    StringBuilder s = new StringBuilder(size());
    for (int i = size() - 1; i >= 0; i--) {
      s.append(getBitAt(i) ? '1' : '0');
    }
    return s.toString();
  }
  
  @Override
  public int hashCode() {
    if (size() == 0) {
      return 0;
    }
    
    int h = 0;
    for (int i = 0; i < size(); i++) {
      int val = getBitAt(i) ? 1 : 0;
      h = h + val * (2^i) * Safe.PRIME;
    }
    
    return h;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof ImmutableBitSet) {
      ImmutableBitSet other = ImmutableBitSet.class.cast(obj);
      if (size() == other.size()) {
        for (int i = 0; i < other.size(); i++) {
          if (getBitAt(i) != other.getBitAt(i)) {
            return false;
          }
        }
        return true;
      } 
      return false;
    }
    return false;
  }
}
