package it.ccse.bandiEsperti.utils;

import java.io.StringWriter;

import java.io.Writer;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;



public class VelocityUtil {
		
		private static VelocityUtil instance;
		
		private VelocityUtil(){}
		
		public static VelocityUtil getInstance(){
        if(instance == null){
            instance = new VelocityUtil();
        }
        return instance;
    }

    private void render(String templatePath, Map<String, Object> context, Writer writer) throws Exception{

        VelocityContext velocityContext = new VelocityContext(context);
        
        Template template = null;
        template = Velocity.getTemplate("file.resource.loader.path");
        template.merge(velocityContext, writer);
       
    }
    
    
    public CharSequence render(String templatePath, Map<String, Object> context) throws Exception{
        StringWriter writer = new StringWriter(5000);
        render(templatePath,context, writer);
        CharSequence ret = writer.getBuffer();
        return ret;
    }

}
