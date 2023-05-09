using Booktopia.Models;
using Microsoft.AspNet.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Controllers
{
    [Authorize(Roles = "User,Colaborator,Administrator")]
    public class InvoicesController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        public ActionResult Index()
        {
            return View();
        }

        [NonAction]
        public JsonResult getAllInvoices()
        {
            var invoices = db.Invoices.Include("Buy");
            //if (!User.IsInRole("Administrator"))
            //    invoices.Where(invoice => invoice.Buys.UserId == User.Identity.GetUserId());
            return Json(invoices.ToList(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult Show(int id)
        {
            Invoice invoice = db.Invoices.Find(id);

            ViewBag.afisareButoane = false;
            if (User.IsInRole("Administrator"))
            {
                ViewBag.afisareButoane = true;
            }
            ViewBag.esteAdmin = User.IsInRole("Administrator");

            if (/*invoice.Buys.UserId == User.Identity.GetUserId()||*/  User.IsInRole("Administrator"))
                return View(invoice);
            else
            {
                TempData["message"] = "Nu puteti vedea facturile altora !";
                return RedirectToAction("Index");
            }

        }

        [HttpPost]
        public ActionResult New(Invoice invoice)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    db.Invoices.Add(invoice);
                    db.SaveChanges();
                    TempData["message"] = "Factura a fost adaugata !";
                    return RedirectToAction("Index");
                }
                else
                {
                    ModelState.Clear();
                    return View();
                }
            }
            catch (Exception e)
            {
                TempData["message"] = "Excepție: " + e.Message;
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }

        public ActionResult Edit(int id)
        {
            Invoice invoice = db.Invoices.Find(id);
            if (/*invoice.Buys.UserId == User.Identity.GetUserId() ||*/ User.IsInRole("Administrator"))
                return View(invoice);
            else
            {
                TempData["message"] = "Nu puteti edita facturile altora !";
                return RedirectToAction("Index");
            }
        }

        [HttpPut]
        public ActionResult Edit(int id, Invoice requestInvoice)
        {
            try
            {
                Invoice invoice = db.Invoices.Find(id);
                if (ModelState.IsValid)
                {
                    if (/*requestInvoice.Buys.UserId == User.Identity.GetUserId() ||*/ User.IsInRole("Administrator"))
                    {
                        if (TryUpdateModel(invoice))
                        {
                            invoice.AdresaFacturare = requestInvoice.AdresaFacturare;
                            invoice.AdresaLivrare = requestInvoice.AdresaLivrare;
                            invoice.Data = requestInvoice.Data;
                            db.SaveChanges();
                        }
                        return RedirectToAction("Index");
                    }
                    else
                    {
                        TempData["message"] = "Nu puteti edita facturile altora !";
                        return View();
                    }
                }
                else
                {
                    ModelState.Clear();
                    return View();
                }
            }
            catch (Exception e)
            {
                TempData["message"] = "Excepție: " + e.Message;
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }

        /*
        [HttpDelete]
        public ActionResult Delete(int id)
        {
            Invoice invoice = db.Invoices.Find(id);
            if (invoice.Buys.UserId == User.Identity.GetUserId() || User.IsInRole("Administrator"))
            { 
                TempData["message"] = "Factura a fost stersa !";
                db.Invoices.Remove(invoice);
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            else
            {
                TempData["message"] = "Factura nu poate fi stearsa !";
                return RedirectToAction("Index");
            }
        }
        */
    }
}