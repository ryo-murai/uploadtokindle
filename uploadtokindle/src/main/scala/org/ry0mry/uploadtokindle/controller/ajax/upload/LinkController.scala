package org.ry0mry.uploadtokindle.controller.ajax.upload

import org.slim3.controller.Controller
import org.slim3.controller.Navigation
import org.ry0mry.uploadtokindle.controller.ScalateController

class LinkController extends ScalateController {

    override def run(): Navigation =  {
      render("ajax/upload/success.ssp", Map("fileName"->"document.mobi"))
      return null;
    }
}