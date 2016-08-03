package particles;

public class ParticleTexture {

	private int textureID;
	private int numberOfRows;
	private boolean additive;
	
	public ParticleTexture(int textureID, int numberOfRows) {
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
	}
	protected boolean useAdditiveBlending(){
		return additive;
	}
	protected int getTextureID() {
		return textureID;
	}
	protected int getNumberOfRows() {
		return numberOfRows;
	}

	
}
