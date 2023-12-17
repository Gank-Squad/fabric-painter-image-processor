package components.filters;

import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.List;

import gui.Base;
import processing.ComponentLogicHelper;
import processing.ProcessImg;

public class ImageDropTargetListener implements DropTargetListener {
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("drag enter");
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
//		System.out.println("drag over");
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// TODO Auto-generated method stub
		System.out.println("drop action changed");
	}

	@Override
	public void dragExit(DropTargetEvent dte) {
		// TODO Auto-generated method stub
		System.out.println("drag exit");
		
	}

	
	// file is dropped onto the thing, so do stuff with it
	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
            dtde.acceptDrop(DnDConstants.ACTION_COPY);
            List<File> files = (List<File>)
                dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            
            if (files.isEmpty())
            	dtde.rejectDrop();
            
//            for (File file : droppedFiles) {
//                
//            }
            
            File file = files.get(0);
            System.out.println("Drag and dropping this file: " + file.getAbsolutePath());
            
            Base.INSTANCE.displayImage.updateImage(ProcessImg.loadImage(file));
            
            Point p = ComponentLogicHelper.getSelectionBoxMaxBounds(Base.INSTANCE.displayImage);
            Base.INSTANCE.xOffsetModel.setValue(0);
            Base.INSTANCE.yOffsetModel.setValue(0);
            Base.INSTANCE.xOffsetModel.setMaximum(p.x);
            Base.INSTANCE.yOffsetModel.setMinimum(-p.y);
            
            dtde.dropComplete(true);
            
        } catch (Exception ex) {
        	ex.printStackTrace();
        	dtde.rejectDrop();
        }
		
	}

}
