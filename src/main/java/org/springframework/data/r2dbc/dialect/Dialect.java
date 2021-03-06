package org.springframework.data.r2dbc.dialect;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mapping.model.SimpleTypeHolder;
import org.springframework.data.r2dbc.dialect.ArrayColumns.Unsupported;
import org.springframework.data.r2dbc.mapping.R2dbcSimpleTypeHolder;

/**
 * Represents a dialect that is implemented by a particular database.
 *
 * @author Mark Paluch
 * @author Jens Schauder
 */
public interface Dialect {

	/**
	 * Returns the {@link BindMarkersFactory} used by this dialect.
	 *
	 * @return the {@link BindMarkersFactory} used by this dialect.
	 */
	BindMarkersFactory getBindMarkersFactory();

	/**
	 * Return a collection of types that are natively supported by this database/driver. Defaults to
	 * {@link Collections#emptySet()}.
	 *
	 * @return a collection of types that are natively supported by this database/driver. Defaults to
	 *         {@link Collections#emptySet()}.
	 */
	default Collection<? extends Class<?>> getSimpleTypes() {
		return Collections.emptySet();
	}

	/**
	 * Return the {@link SimpleTypeHolder} for this dialect.
	 *
	 * @return the {@link SimpleTypeHolder} for this dialect.
	 * @see #getSimpleTypes()
	 */
	default SimpleTypeHolder getSimpleTypeHolder() {

		Set<Class<?>> simpleTypes = new HashSet<>(getSimpleTypes());
		simpleTypes.addAll(R2dbcSimpleTypeHolder.R2DBC_SIMPLE_TYPES);

		return new SimpleTypeHolder(simpleTypes, true);
	}

	/**
	 * Return the {@link LimitClause} used by this dialect.
	 *
	 * @return the {@link LimitClause} used by this dialect.
	 */
	LimitClause limit();

	/**
	 * Returns the array support object that describes how array-typed columns are supported by this dialect.
	 *
	 * @return the array support object that describes how array-typed columns are supported by this dialect.
	 */
	default ArrayColumns getArraySupport() {
		return Unsupported.INSTANCE;
	}
}
