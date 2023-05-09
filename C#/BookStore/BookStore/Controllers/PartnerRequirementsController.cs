using Booktopia.Models;
using Microsoft.AspNet.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Controllers
{
    [Authorize(Roles = "Colaborator,Administrator")]
    public class PartnerRequirementsController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        public ActionResult Index()
        {
            var requirements = db.PartnerRequirements.Include("Book").Include("User").Where(requirement => requirement.Status == 0);
            if (User.IsInRole("Colaborator"))
                requirements.Where(requirement => requirement.UserId == User.Identity.GetUserId());

            ViewBag.Requirements = requirements;
            ViewBag.RequirementsJson = GetAllRequirements();

            ViewBag.Rol = (User.IsInRole("Administrator")) ? "Administrator" : "Colaborator";
            return View();
        }

        [NonAction]
        public JsonResult GetAllRequirements()
        {
            var requirements = db.PartnerRequirements.Include("Book").Include("User");
            if (User.IsInRole("Colaborator"))
                requirements.Where(requirement => requirement.UserId == User.Identity.GetUserId());
            return Json(requirements.ToList(), JsonRequestBehavior.AllowGet);
        }

        [HttpDelete]
        public ActionResult Delete(int id)
        {
            PartnerRequirement requirement = db.PartnerRequirements.Find(id);
            if (requirement.Status == 0 && (User.IsInRole("Administrator") || (User.Identity.GetUserId() == requirement.UserId)))
            {
                TempData["message"] = "Cererea a fost ștearsă !";
                db.PartnerRequirements.Remove(requirement);
                db.Books.Remove(db.Books.Find(requirement.BookId));
                db.SaveChanges();
                return Content("Succes");
            }
            else
            {
                TempData["message"] = "Cererea nu poate fi ștearsă !";
                return Content("NoRight");
            }
        }

        [HttpPost]
        public ActionResult ChangeRequirementStatus(int id, string newStatus)
        {
            PartnerRequirement partnerRequirement = db.PartnerRequirements.Find(id);
            if (newStatus == "Accept")
            {
                partnerRequirement.Status = 1;
                Book book = db.Books.Find(partnerRequirement.BookId);
                book.Status = 1;
                db.SaveChanges();
                return Content("Succes");
            }
            else if (newStatus == "Reject")
            {
                partnerRequirement.Status = -1;
                db.Books.Remove(db.Books.Find(partnerRequirement.BookId));
                db.SaveChanges();
                return Content("Succes");
            }
            return Content("Error");
        }
    }
}