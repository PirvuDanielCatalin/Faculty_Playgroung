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
    public class BuysController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        [Authorize(Roles = "Colaborator,Administrator")]
        public ActionResult Index()
        {
            return View();
        }

        [NonAction]
        public JsonResult getAllBuys()
        {
            var buys = db.Buys.Include("Book")
                              .Include("Invoice")
                              .Include("User");
            //if (User.IsInRole("Colaborator"))
            //   buys.Where(buy => buy.Book.PartnerRequirement.UserId == User.Identity.GetUserId());
            return Json(buys.ToList(), JsonRequestBehavior.AllowGet);
        }

        public ActionResult New()
        {
            return View();
        }

        [HttpPost]
        public ActionResult New(Buy buy)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    db.Buys.Add(buy);
                    db.SaveChanges();
                    TempData["message"] = "Comanda a fost procesata !";
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

        public ActionResult Show(int id)
        {
            Buy buy = db.Buys.Find(id);
            if (User.Identity.GetUserId() == buy.UserId || (User.IsInRole("Administrator")))
                return View(buy);
            else
            {
                TempData["message"] = "Nu puteti vedea comenzilor altora !";
                return RedirectToRoute("/home/index");
            }
        }

        /*
        [HttpDelete]
        public ActionResult Delete(int id)
        {
            Buy buy = db.Buys.Find(id);
            if (User.Identity.GetUserId() == buy.UserId || (User.IsInRole("Administrator")))
            {
                TempData["message"] = "Comanda a fost stearsa !";
                db.Buys.Remove(buy);
                db.SaveChanges();
            }
            else
                TempData["message"] = "Nu puteti sterge comenzilor altora !";
            return RedirectToRoute("/home/index");
        }
        */
    }
}