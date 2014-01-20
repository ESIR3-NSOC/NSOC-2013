package esir.dom13.nsoc.resources;
import org.daum.planrouge.server.cache.MemCache;
import org.kevoree.annotation.ComponentType;
import org.kevoree.library.javase.webserver.AbstractPage;
import org.kevoree.library.javase.webserver.KevoreeHttpRequest;
import org.kevoree.library.javase.webserver.KevoreeHttpResponse;
import org.kevoree.log.Log;


@ComponentType
public class ResourcesPage extends AbstractPage {

    @Override
    public KevoreeHttpResponse process(KevoreeHttpRequest kevoreeHttpRequest, KevoreeHttpResponse kevoreeHttpResponse)
    {
        String url = kevoreeHttpRequest.getUrl().substring(1);
        Log.debug("ResourcePage ::: "+url);
        kevoreeHttpResponse.setRawContent(MemCache.getRessource(url));
        return kevoreeHttpResponse;
    }


}