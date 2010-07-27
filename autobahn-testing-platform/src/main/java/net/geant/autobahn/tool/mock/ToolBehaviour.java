package net.geant.autobahn.tool.mock;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ToolBehaviour", namespace="mock.tool.autobahn.geant.net",
	propOrder={ "result", "delay" 
})
public class ToolBehaviour {

	private Result result; 
	private int delay;
	
	public ToolBehaviour() {
		
	}
	
	public ToolBehaviour(Result result, int delay) {
		super();
		this.result = result;
		this.delay = delay;
	}

	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	public int getDelay() {
		return delay;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
}
