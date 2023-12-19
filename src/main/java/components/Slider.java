package components;

import java.io.Serializable;

import javax.swing.BoundedRangeModel;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


// purpose of this class is to be a slider, but with 3 text boxes, which correspond to min value, current value, max value
// all should optionally be modifiable and visible, with an optional specifiable clamp on input by user
// should ideally also not look super terrible
public class Slider extends JComponent {

	BoundedRangeModel sliderModel;
	ChangeListener changeListener = new ModelListener();
	
	Slider()
	{
		
	}
	Slider(int min, int max, int value)
	{
		
	}
	
	public void setRangeModel(BoundedRangeModel model)
	{
		if (sliderModel != null)
		{
			sliderModel.removeChangeListener(changeListener);
			changeListener = null;
		}
	}
	
	public void addChangeListener(ChangeListener changeListener)
	{
		listenerList.add(ChangeListener.class, changeListener);
	}
	
	
	private class ModelListener implements ChangeListener, Serializable {
        public void stateChanged(ChangeEvent e) {
//            fireStateChanged();
        }
    }
	
}
