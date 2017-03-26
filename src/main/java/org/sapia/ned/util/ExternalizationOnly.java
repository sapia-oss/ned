package org.sapia.ned.util;

import java.io.Externalizable;

/**
 * Tags the empty/no-args constructor of {@link Externalizable} class, when such constructors 
 * are strictly meant to be called through reflection in the context of externalization.
 * 
 * @author yduchesne
 *
 */
public @interface ExternalizationOnly {

}
