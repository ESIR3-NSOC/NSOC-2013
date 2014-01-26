package esir.dom13.nsoc.pageWeb;

import org.kevoree.annotation.ComponentType;
import org.kevoree.library.javase.webserver.AbstractPage;
import org.kevoree.library.javase.webserver.KevoreeHttpRequest;
import org.kevoree.library.javase.webserver.KevoreeHttpResponse;

/**
 * Created by Clement on 20/01/14.
 */
@ComponentType
public class TestPage extends AbstractPage {

    @Override
    public KevoreeHttpResponse process(KevoreeHttpRequest kevoreeHttpRequest, KevoreeHttpResponse kevoreeHttpResponse) {
        //String page = new String(MemCache.getRessource("index.html"));
        kevoreeHttpResponse.setContent("");
        return kevoreeHttpResponse;
    }


}

