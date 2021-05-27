package com.whitesource.remediation.encoder;

import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.owasp.esapi.codecs.UnixCodec;
import org.owasp.esapi.codecs.WindowsCodec;

/**
 * Remediation Solver static class written by WhiteSource with the community ❤. Here you can find
 * wrapper functions to secure unsafe operations in your code.
 */
public class Encode {

  /**
   * Encoding operating system parameters.
   *
   * @param params The parameters for the operating systems.
   * @return Encoded parameters.
   */
  public static String OSParameterEncoder(@NonNull final String params)
      throws UnsupportedOperationException {
    if (SystemUtils.IS_OS_WINDOWS) {
      return Utils.esapiEncoder(new WindowsCodec(), params);
    } else if (SystemUtils.IS_OS_UNIX) {
      return Utils.esapiEncoder(new UnixCodec(), params);
    }

    throw new UnsupportedOperationException("Unsupported encoder for operating system");
  }

  /**
   * Encoding content for logs.
   *
   * @param contents arrays {@link Object} contains all the contents.
   * @return encoded log content.
   */
  public static String[] multiLogContentEncoder(@NonNull final Object[] contents) {

    List<String> results = new ArrayList<>();

    for (Object content : contents) {
      results.add(logContentEncoder(content));
    }
    return results.toArray(String[]::new);
  }

  /**
   * Encoding content for logs.
   *
   * @param content {@link Object} contains the content.
   * @return encoded log content.
   */
  public static String logContentEncoder(@NonNull final Object content) {
    return content
        .toString()
        .replaceAll("[\n|\r|\t]", "_")
        .replaceAll("<", "&lt")
        .replaceAll(">", "&gt");
  }

  /**
   * Encoding content to prevent crlf injection.
   *
   * @param content {@link Object} contains the content.
   * @return encoded Html content.
   */
  public static String crlfBasicEncoder(@NonNull final Object content) {
    return content
            .toString()
            .replaceAll("[\n|\r]", "");
  }

  /**
   * Encoding content to prevent crlf injection according to Apache Commons.
   *
   * @param content {@link Object} contains the content.
   * @return encoded Html content.
   */
  public static String crlfApacheEncoder(@NonNull final Object content) {
    return StringUtils.replaceEach(
            content.toString(),
            new String[] {"\n", "\\n", "\r", "\\r", "%0d", "%0D", "%0a", "%0A", "\025"},
            new String[] {"", "", "", "", "", "", "", "", ""});
  }

  /**
   * This method encodes for JavaScript strings contained within HTML script blocks.
   *
   * @param content {@link Object} contains the content.
   * @return encoded JavaScript block.
   */
  public static String forJavaScriptBlock(@NonNull final String content) {

    return org.owasp.encoder.Encode.forJavaScriptBlock(content);
  }

  /**
   * This method encodes for HTML text content. It does not escape quotation characters and is thus
   * unsafe for use with HTML attributes. Use either forHtml or forHtmlAttribute for those methods.
   *
   * @param content {@link Object} contains the content.
   * @return encoded Html content.
   */
  public static String forHtmlContent(@NonNull final String content) {

    return org.owasp.encoder.Encode.forHtmlContent(content);
  }

  /**
   * This method encodes for HTML text attributes.
   *
   * @param content {@link Object} contains the content.
   * @return encoded Html Attribute.
   */
  public static String forHtmlAttribute(@NonNull final String content) {

    return org.owasp.encoder.Encode.forHtmlAttribute(content);
  }

  /**
   * Encodes for a JavaScript string. It is safe for use in HTML script attributes (such as
   * onclick), script blocks, JSON files, and JavaScript source. The caller MUST provide the
   * surrounding quotation characters for the string. Since this performs additional encoding so it
   * can work in all of the JavaScript contexts listed, it may be slightly less efficient than using
   * one of the methods targetted to a specific JavaScript context (forJavaScriptAttribute(String),
   * forJavaScriptBlock(java.lang.String), forJavaScriptSource(java.lang.String)). Unless you are
   * interested in saving a few bytes of output or are writing a framework on top of this library,
   * it is recommend that you use this method over the others.
   *
   * @param content {@link Object} contains the content.
   * @return encoded JavaScript string.
   */
  public static String forJavaScript(@NonNull final String content) {

    return org.owasp.encoder.Encode.forJavaScript(content);
  }

  /**
   * Encodes for CSS strings. The context must be surrounded by quotation characters. It is safe for
   * use in both style blocks and attributes in HTML.
   *
   * @param content {@link Object} contains the content.
   * @return encoded CSS String.
   */
  public static String forCssString(@NonNull final String content) {

    return org.owasp.encoder.Encode.forCssString(content);
  }

  /**
   * Performs percent-encoding for a component of a URI, such as a query parameter name or value,
   * path or query-string. In particular this method insures that special characters in the
   * component do not get interpreted as part of another component.
   *
   * @param content {@link Object} contains the content.
   * @return encoded Uri component.
   */
  public static String forUriComponent(@NonNull final String content) {

    return org.owasp.encoder.Encode.forUriComponent(content);
  }

  /**
   * Encodes for CSS URL contexts. The context must be surrounded by "url(" and ")". It is safe for
   * use in both style blocks and attributes in HTML. Note: this does not do any checking on the
   * quality or safety of the URL itself. The caller should insure that the URL is safe for
   * embedding (e.g. input validation) by other means.
   *
   * @param content {@link Object} contains the content.
   * @return encoded CSS url.
   */
  public static String forCssUrl(@NonNull final String content) {

    return org.owasp.encoder.Encode.forCssUrl(content);
  }

  /**
   * Encodes for unquoted HTML attribute values. forHtml(String) or forHtmlAttribute(String) should
   * usually be preferred over this method as quoted attributes are XHTML compliant. When using this
   * method, the caller is not required to provide quotes around the attribute (since it is encoded
   * for such context). The caller should make sure that the attribute value does not abut unsafe
   * characters--and thus should usually err on the side of including a space character after the
   * value. Use of this method is discouraged as quoted attributes are generally more compatible and
   * safer. Also note, that no attempt has been made to optimize this encoding, though it is still
   * probably faster than other encoding libraries.
   *
   * @param content {@link Object} contains the content.
   * @return encoded Html unquoted Attribute.
   */
  public static String forHtmlUnquotedAttribute(@NonNull final String content) {

    return org.owasp.encoder.Encode.forHtmlUnquotedAttribute(content);
  }

  /**
   * This method encodes for JavaScript strings contained within HTML script attributes (such as
   * onclick). It is NOT safe for use in script blocks. The caller MUST provide the surrounding
   * quotation characters. This method performs the same encode as forJavaScript(String) with the
   * exception that / is not escaped.
   *
   * @param content {@link Object} contains the content.
   * @return encoded JavaScript attribute.
   */
  public static String forJavaScriptAttribute(@NonNull final String content) {

    return org.owasp.encoder.Encode.forJavaScriptAttribute(content);
  }
}
