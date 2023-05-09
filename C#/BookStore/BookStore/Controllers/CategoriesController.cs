using Booktopia.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Controllers
{


    public class CategoriesController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        // GET: Categories
        [Authorize(Roles = "Administrator")]
        public ActionResult Index()
        {
            ViewBag.categories = db.Categories;
            return View();
        }

        [NonAction]
        [Authorize(Roles = "User,Colaborator,Administrator")]
        public JsonResult getAllCategories()
        {
            var categories = db.Categories.Include("BookCategory");
            return Json(categories.ToList(), JsonRequestBehavior.AllowGet);
        }

        [Authorize(Roles = "Administrator")]
        public ActionResult Show(int id)
        {
            Category category = db.Categories.Find(id);
            ViewBag.afisareButoane = false;
            if (User.IsInRole("Administrator"))
            {
                ViewBag.afisareButoane = true;
            }
            ViewBag.esteAdmin = User.IsInRole("Administrator");
            return View(category);

        }

        [Authorize(Roles = "Administrator")]
        public ActionResult New()
        {
            return View();
        }

        [HttpPost]
        [Authorize(Roles = "Administrator")]
        public ActionResult New(Category category)
        {
            try
            {
                if (User.IsInRole("Administrator"))
                {
                    if (ModelState.IsValid)
                    {
                        db.Categories.Add(category);
                        db.SaveChanges();
                        TempData["status"] = true;
                        TempData["message"] = "Categoria a fost adăugată!";
                        return RedirectToAction("Index");
                    }
                    else
                    {
                        TempData["status"] = false;
                        TempData["message"] = "Eroare la adăugarea categoriei!";
                        return RedirectToAction("Index");
                    }
                }
                else
                {
                    TempData["status"] = false;
                    TempData["message"] = "Doar administratorul pot adăuga categorii!";
                    return RedirectToAction("Index");

                }
            }
            catch (Exception e)
            {
                TempData["message"] = "Excepție: " + e.Message;
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }

        [Authorize(Roles = "Administrator")]
        public ActionResult Edit(int id)
        {
            Category category = db.Categories.Find(id);
            if (User.IsInRole("Administrator"))
            {
                return View(category);
            }
            else
            {
                TempData["message"] = "Doar administratorul poate face asta !";
                return RedirectToAction("Index");
            }
        }

        [HttpPut]
        [Authorize(Roles = "Administrator")]
        public ActionResult Edit(int id, Category requestCategory)
        {
            try
            {
                Category category = db.Categories.Find(id);
                if (ModelState.IsValid)
                {
                    if (User.IsInRole("Administrator"))
                    {
                        if (TryUpdateModel(category))
                        {
                            category.Nume = requestCategory.Nume;
                            db.SaveChanges();
                        }
                        return RedirectToAction("Index");
                    }
                    else
                    {
                        TempData["message"] = "Doar administratorul poate face asta !";
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
        [Authorize(Roles = "Administrator")]
        public ActionResult Delete(int id)
        {
            Category category = db.Categories.Find(id);
            TempData["message"] = "Categoria " + category.Nume + " a fost stersa !";
            db.Categories.Remove(category);
            db.SaveChanges();
            return RedirectToAction("Index");
        }
    }
}