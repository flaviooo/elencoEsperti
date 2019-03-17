package it.ccse.bandiEsperti.zk.composers;

import java.net.URL;
import java.net.URLConnection;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.North;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

public class AnteprimaDocComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Iframe frame;

	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		Window win = (Window) comp;
		byte arrCnt[] = (byte []) arg.get("byteArr");
		String title = (String) arg.get("winTitle");
		String estensione = (String) arg.get("estensione");
		String contentType = (String) arg.get("contentType");
		String titlePDF = "anteprima."+estensione;
		 		 ////////////////
		 
		Page page = comp.getPage();
		show(titlePDF, arrCnt, page);
		AMedia media = new AMedia(titlePDF, estensione, contentType, arrCnt);
		frame.setSrc(titlePDF);
		frame.setContent(media);
		win.setTitle(title);
	}
	
	public static void show( String name, byte[] arr, Page page) throws SuspendNotAllowedException, InterruptedException {
	    AMedia media = new AMedia(name, "pdf", "application/pdf", arr);

	    final Window window = new Window();
	    
	    window.setClosable(false);
	    window.setSizable(false);
	    
	    Iframe iframe = new Iframe();
	    iframe.setSrc(name);
	    iframe.setContent(media);

	    Borderlayout borderlayout = new Borderlayout();

	    North north = new North();

	    Toolbar toolbar = new Toolbar();
	    toolbar.setAlign("end");

	    Toolbarbutton close = new Toolbarbutton(null, "/icons/16x16/actions/process-stop.png");
	    close.setTooltiptext("Cerrar");
//	    close.addEventListener("onClick", (Event t) -> {
//	        window.onClose();
//	    });

	    toolbar.appendChild(close);

	    north.appendChild(toolbar);

	    borderlayout.appendChild(north);

	    Center cntr = new Center();
	    cntr.appendChild(iframe);

	    borderlayout.appendChild(cntr);
	    window.appendChild(borderlayout);

//	    iframe.setWidth("100%");
//	    iframe.setHeight("100%");
//	    window.setWidth("80%");
//	    window.setHeight("80%");
	    window.setPage(page);
	    window.doModal();
	}
	 


}
