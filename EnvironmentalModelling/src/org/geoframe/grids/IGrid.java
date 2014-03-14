package org.geoframe.grids;

public interface IGrid {
	
	/**
	 * sets the Boundary conditions
	 */
	public abstract void setPositions();
	public abstract void setBoundaryConditions();
	
	/**
	 * sets the values on the grid, for instance the initial conditions, but also the values at any time step
	 */
    public abstract void setValues();
}
