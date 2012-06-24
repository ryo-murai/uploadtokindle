package org.ry0mry.uploadtokindle.controller

import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import org.fusesource.scalate.TemplateEngine
import org.fusesource.scalate.servlet.ServletResourceLoader
import org.fusesource.scalate.servlet.ServletRenderContext

abstract class ScalateController extends Controller {
  protected val templateEngine = new TemplateEngine

  protected def render(path: String, param: Map[String,Object]) {
    templateEngine.resourceLoader = new ServletResourceLoader(servletContext)
    val context = new ServletRenderContext(templateEngine, request, response, servletContext)
    context.render(path, param)
  }
}