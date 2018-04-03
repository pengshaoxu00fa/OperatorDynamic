package fel.operatordynamic.uilib.base;

public interface IDragView {
	int TAG_DRAG = 0x886;
	int TAG_CLOSE = 0x887;
	int TAG_CENTRE = 0x888;
	int TAG_TEXT = 0x889;
	int getFlag();
	boolean isInit();
	void setInit(boolean isInit);
	String getIndex();
	void setIndex(String index);
	
	float[] getPos();//原始位置
	void setPos(float[] pos);
	
	float[] getSize();//当前大小
	void setSize(float[] size);

}
