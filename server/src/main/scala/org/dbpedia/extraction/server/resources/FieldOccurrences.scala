package org.dbpedia.extraction.server.resources

import javax.ws.rs._

import org.dbpedia.extraction.server.Server
import org.dbpedia.extraction.server.stats.MappingStats
import org.dbpedia.extraction.server.stats.PropertyCollector
import org.dbpedia.extraction.server.util.StringUtils.urlEncode
import org.dbpedia.extraction.util.Language
import org.dbpedia.extraction.util.WikiUtil.{wikiDecode, wikiEncode}
import org.dbpedia.extraction.wikiparser.Namespace

/**
 * Displays the occurrences of a field
 */
@Path("/fieldoccurrences/{lang}/")
class FieldOccurrences(@PathParam("lang") langCode: String, @QueryParam("template") var template: String, @QueryParam("field") var field: String, @QueryParam("p") password: String)
{
  // get canonical template name
  template = wikiDecode(template)

  private val language = Language.getOrElse(langCode, throw new WebApplicationException(new Exception("invalid language "+langCode), 404))

  if (! Server.instance.managers.contains(language)) throw new WebApplicationException(new Exception("language "+langCode+" not defined in server"), 404)

  private val mappingUrlPrefix = Server.instance.paths.pagesUrl+"/"+Namespace.mappings(language).name(language).replace(' ','_')+":"

  private val manager = Server.instance.managers(language)

  @GET
  @Produces(Array("application/xhtml+xml"))
  def get = {

    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
      {ServerHeader.getHeader(s"Occurrences of field $field",true)}
      <body>
        <h2 align="center">Occurences of template <a href={language.baseUri + "/wiki/" + manager.templateNamespace + wikiEncode(template)}>{template}</a>'s field  {wikiEncode(field)}</h2>

        <table class="tablesorter table myTable table-condensed" style="width:500px; margin:auto;margin-top:10px">
          <thead>
            <tr>
              <th>page</th> <th>field "{wikiEncode(field)}"</th>
            </tr>
          </thead>
          <tbody>
            {
            // TODO: loop field occurrences
            }
          </tbody>
        </table>
      </body>
    </html>
  }

}