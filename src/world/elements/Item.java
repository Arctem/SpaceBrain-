package world.elements;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import world.elements.Ship.Ship;
import world.elements.libs.ImageLib;
import world.elements.libs.ImageType;
import world.elements.physics.Location;

public class Item extends SpaceThing {


	private ItemType type;
	private double power;
	private boolean pickedUp;
	
	public Item(Location location, ItemType type, double power){
		super();
		this.location = location;
		this.type = type;
		this.power = power;
		pickedUp = false;
		centerX = centerY = 5;
		setSpriteType();
		compile();
	}
	
	private void setSpriteType() {
			if(power < 3) {
				this.spriteType = ImageType.ITEM_1;
			}
			else if(power < 5) {
				this.spriteType = ImageType.ITEM_3;
			}
			else
				this.spriteType = ImageType.ITEM_2;
	}
	
	@Override
	public void paint(Graphics g, double originX, double originY) {
		BufferedImage sprite = ImageLib.getImage(spriteType);
		
		/* if(type == ItemType.SHEILDS) {
			BufferedImage tintImg = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
			Graphics2D g2d = (Graphics2D) tintImg.getGraphics();
			Color tintCol = new Color(255, 0, 0, 255);
			g2d.setXORMode(tintCol);
			g2d.drawImage(sprite, null, 0, 0);
			sprite = tintImg;
		} */
		
		g.drawImage(sprite, (int) (location.getxLocal() - originX), (int) (location.getyLocal() - originY), null);
	}
	
	@Override
	//THIS IS THE BULLET HITBOX@@@!
	public void compile() {
		// TODO: This will also need to be changed when we figure out why library hitboxes aren't workin
		hitBox = new Polygon();
		hitBox.addPoint((int)getX(), (int)getY());
		hitBox.addPoint((int) getX() + 10, (int) getY());
		hitBox.addPoint((int) getX() + 10, (int) getY() + 10);
		hitBox.addPoint((int) getX(), (int) getY() + 10);
		rotateHitBox(vector.getRadians());
	}

	@Override
	public void collision(SpaceThing thing) {
		if(thing instanceof Item || thing instanceof Weapon){
			double originalX = vector.getxSpeed();
			double originalY = vector.getySpeed();
			this.accelX(0.8*(thing.getxSpeed()-originalX));
			this.accelY(0.8*(thing.getySpeed()-originalY));
			thing.accelX(0.8*(originalX-thing.getxSpeed()));
			thing.accelY(0.8*(originalY-thing.getySpeed()));
			while (isColliding(thing)) {
				displace(thing);
				thing.displace(this);
			}
		} else if(thing instanceof Ship){
			pickedUp = true;
			((Ship) thing).pickupItem(this);
		}
	}

	@Override
	public boolean isDead() {
		return pickedUp;
	}

	@Override
	public void die(){}
	
	public ItemType getType() {
		return type;
	}
	
	public void drop(){
		pickedUp = false;
	}

	public void setType(ItemType type) {
		this.type = type;
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = power;
	}

	public BufferedImage getSprite(){
		return 	ImageLib.getImage(spriteType);
	}
}
