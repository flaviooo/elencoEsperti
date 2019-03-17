package it.ccse.bandiEsperti.zk.uploaders;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.zkoss.zk.au.http.AuUploader;

public class FileUploader extends AuUploader {

	@Override
	protected String handleError(Throwable ex) {
		if(ex instanceof SizeLimitExceededException){
			 SizeLimitExceededException e = (SizeLimitExceededException) ex;
	         return "File troppo grande. La dimensione massima consentita è di "+e.getPermittedSize()/(1024*1024) + " MB.";       
	      }         
	      return super.handleError(ex);     
	}
	
}
