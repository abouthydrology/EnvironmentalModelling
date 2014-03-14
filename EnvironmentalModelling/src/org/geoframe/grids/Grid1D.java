/**
 * One dimensional grid for finite difference centered in space (FTCS) algorithms
 */
package org.geoframe.grids;
/*
 *  To see how to access classes from other projects in Eclipse look at:
 *  http://stackoverflow.com/questions/9163501/eclipse-import-folder-of-source-code-to-other-projects
 *  
 */
import org.geoframe.io.TextIO;

//import package org.geoframe.io;

/**
 * @author riccardo rigon
 * @version 0.1
 *
 */
public class Grid1D implements IGrid{

	/**
	 * left boundary
	 */
    public double xl;
    /**
     * right boundary position
     */
	public double xr;
	/**
	 * interval of integration position
	 */
	public double dx;
	/**
	 * number of intervals  dx=(xr-xl)/(iMax-1)
	 */
	public int iMax;
	/**
	 * instant of time at which the grid refers to
	 */
	public double time;
	/**
	 * vector containing the grid data which, in this case means the coordinates and the values
	 */
	public double[][] linearSpace=null;
	//Hard coded at the moment
	private static final long iMin=3;
	private static final int MINFACTOR=100;
	
	/**
	 * sets the boundary positions by entering the values from the console
	 */
	@Override
	public void setPositions(){
		System.out.println("Enter the left boundary position");
		xl=TextIO.getDouble();
		System.out.println("Enter the right boundary position");
		xr=TextIO.getDouble();
		System.out.println("Enter the number of grid cells");
		while(this.iMax < Grid1D.iMin){
			this.iMax=TextIO.getInt();
		}
		this.dx=(this.xr-this.xl)/(this.iMax-1);
		//Allocate the linear Space
		this.linearSpace=new double[2][iMax];
		for(int i=0;i<=iMax-1;i++){
			this.linearSpace[0][i]=i*this.dx+xl;
		}
		
	}
	/**
	 * sets the boundary positions by getting the values from somewhere
	 * @param xl left boundary 
	 * @param xr right boundary
	 */
	public void setPositions(double xl,double xr,int iMax) {
		
		this.xl=xl;
		if(xr < this.xl){
			throw new RuntimeException("Left boundary higher than right boundary");
		}
		this.xr=xr;
		this.iMax=iMax;
		this.dx=(this.xl-this.xr)/(this.iMax-1);
		if(this.dx < MINFACTOR*Double.MIN_VALUE){
			throw new RuntimeException("Interval of integration is too small for being representable");
		}
		//Allocate the linear Space
		this.linearSpace=new double[2][iMax];
		for(int i=0;i<=iMax-1;i++){
			this.linearSpace[0][i]=i*this.dx+xl;
			System.out.println(this.linearSpace[0][i]);
		}
		
	}
	
	/**
	 * sets the boundary conditions by getting them form STDIO. Te user has to know which kind
	 * of boundary condition is going to impose
	 * 
	 */
	
	@Override
	public void setBoundaryConditions() {
		//Check 
		if(linearSpace!=null){
		System.out.println("Enter the left boundary condition");
		linearSpace[1][0]=TextIO.getDouble();
		System.out.println("Enter the right boundary condition");
			linearSpace[1][linearSpace[0].length-1]=TextIO.getDouble();
		}else{
			throw new RuntimeException("LinearSpace Not allocated");
		}
		
		
	}
       //Setting values is not trivial at all. Below method is a pretty specific case
	   // connected to solving the linear heat equation with half of the domain at T=20 and
	   // the other at T=0
	@Override
	public void setValues() {
		//Tl  =20 C
		//Tr = 0 C
		int middlePoint = iMax/2;
		//System.out.println("Middle Point "+ middlePoint);
           for(int i=0;i<middlePoint;i++){
        	   this.linearSpace[1][i]=20;
           }
           for(int i=middlePoint+1;i<iMax;i++){
        	   this.linearSpace[1][i]=0;
           }
	}
	
	//A more general setValues could take them from a FILE together with the positions. 
	
	/**
	 * prints the linear Space on the screen
	 * 
	 */
	public void print(){
		System.out.println("Grid at time "+time);
		System.out.println("x\tT[C]");
		for (int i=0;i< linearSpace[0].length; i++){
			System.out.println(linearSpace[0][i]+"\t"+linearSpace[1][i]);
		}
	
	}
	/**
	 * write the linear vector on File
	 */
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing and printing the linearSpace
		System.out.println("This is Grid1D main");
		Grid1D grid=new Grid1D();
		//Set The working directory
		grid.setPositions();
		grid.setBoundaryConditions();
		grid.setValues();
		grid.print();
		System.out.println("End of computation");

	}
	

}
