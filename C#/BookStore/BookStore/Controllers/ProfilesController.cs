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
    public class ProfilesController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();
        // GET: Profiles
        [Authorize(Roles = "Administrator")]
        public ActionResult Index()
        {
            return View();
        }
        [NonAction]
        [Authorize(Roles = "Administrator")]
        public JsonResult getAllProfiles()
        {
            var requirements = db.Profiles.Include("User");
            return Json(requirements.ToList(), JsonRequestBehavior.AllowGet);
        }
        public ActionResult New()
        {
            return View();
        }
        [HttpPost]
        public ActionResult New(Profile profile)
        {
            try
            {

                if (ModelState.IsValid)
                {
                    db.Profiles.Add(profile);
                    db.SaveChanges();
                    TempData["message"] = "Profilul a fost creat !";
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
            Profile profile = db.Profiles.Find(id);
            return View(profile);
        }
        public ActionResult Edit(int id)
        {
            Profile profile = db.Profiles.Find(id);
            if (User.IsInRole("Administrator") || User.Identity.GetUserId() == profile.UserId)
            {
                return View(profile);
            }
            else
            {
                TempData["message"] = "Nu puteti edita profilul altcuiva !";
                return RedirectToAction("Index");
            }
        }
        [HttpPut]
        public ActionResult Edit(int id, Profile requestProfile)
        {
            try
            {
                Profile profile = db.Profiles.Find(id);
                if (ModelState.IsValid)
                {
                    if (User.IsInRole("Administrator") || User.Identity.GetUserId() == profile.UserId)
                    {
                        if (TryUpdateModel(profile))
                        {
                            profile.Nume = requestProfile.Nume;
                            profile.Prenume = requestProfile.Prenume;
                            profile.Adresa = requestProfile.Adresa;
                            db.SaveChanges();
                        }
                        return RedirectToAction("Index");
                    }
                    else
                    {
                        TempData["message"] = "Nu puteti edita profilul altcuiva !";
                        return RedirectToAction("Index");
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
        [HttpDelete]
        public ActionResult Delete(int id)
        {
            Profile profile = db.Profiles.Find(id);
            if (User.IsInRole("Administrator") || User.Identity.GetUserId() == profile.UserId)
            {
                TempData["message"] = "Profilul a fost sters !";
                db.Profiles.Remove(profile);
                db.SaveChanges();
            }
            else
                TempData["message"] = "Nu puteti sterge profilul altcuiva !";
            return RedirectToAction("Index");
        }
    }
}