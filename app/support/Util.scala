package support

import com.google.common.base.Charsets
import com.google.common.io.BaseEncoding

/**
  * Created by g.gerasimov on 22.04.2017.
  */
object Util {
  def encodeUrl(string: String): String =
    java.net.URLEncoder.encode(string, java.nio.charset.StandardCharsets.UTF_8.displayName())

  def base64Encode(string: String): String =
    BaseEncoding.base64().encode(string.getBytes(Charsets.UTF_8))
}
