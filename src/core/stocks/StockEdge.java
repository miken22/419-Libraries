package core.stocks;

import core.components.Edge;

public class StockEdge extends Edge {

	private double correlationCoefficient;
	
	private Company v1;
	private Company v2;
	
	public StockEdge() {
		correlationCoefficient = 0;
	}
	
	public StockEdge(double coeff) {
		this.correlationCoefficient = coeff;
	}
	
	public StockEdge(double coeff, Company v1, Company v2) {
		this.correlationCoefficient = coeff;
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public String toString() {
		return String.valueOf(correlationCoefficient);
	}
	
	public double getCorrelation() {
		return correlationCoefficient;
	}
	
	public Company getV1() {
		return v1;
	}

	public Company getV2() {
		return v2;
	}
}
