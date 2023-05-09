using Booktopia.Models;
using Microsoft.AspNet.Identity;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Controllers
{
    public class BooksController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        public ActionResult Index()
        {
            ViewBag.books = db.Books.Where(book => book.Status == 1);
            return View();
        }

        [NonAction]
        public string GetAllBooks()
        {
            var books = db.Books.Include("BookCategories")
                                .Include("PartnerRequirements")
                                .Include("Ratings")
                                .Include("BookComments")
                                .Include("Buys")
                                .Include("Stock");
            //if (!User.IsInRole("Administrator"))
            //{
            //    var bookS = books.Where(book => book.Status == 1);
            //    return JsonConvert.SerializeObject(bookS.ToList());
            //    //return Json(bookS.ToList(), JsonRequestBehavior.AllowGet);
            //}
            ////return Json(books.ToList(), JsonRequestBehavior.AllowGet);
            return JsonConvert.SerializeObject(books.ToList());
        }

        public ActionResult Show(int id)
        {
            Book book = db.Books.Find(id);
            if (book == null)
            {
                TempData["message"] = "Nu există cartea cu ID-ul egal cu " + id + " !";
                return View("~/Views/Shared/NoRight.cshtml");
            }

            // Extragem idul colaboratorului care a creat cartea adică cel care a facut request-ul pt carte cu cantitate 0
            var BookPartnerRequirements = db.PartnerRequirements.Where(pr => pr.BookId == id && pr.Cantitate == 0);
            var UserId = (BookPartnerRequirements.Count() != 0) ? BookPartnerRequirements.First().UserId : "";

            // Se afiseaza butoanele de editare si stergere doar pt Administratori si pt colaboratorul care a creat cartea
            ViewBag.afisareButoane = (User.IsInRole("Administrator") || User.Identity.GetUserId() == UserId) ? true : false;

            // Cei care vad cartea care nu se gaseste in magazin sunt adminii si colaboratorul care a creat-o
            if (book.Status == 0 && (!User.Identity.IsAuthenticated ||
                                               User.IsInRole("User") ||
                                     (User.IsInRole("Colaborator") && User.Identity.GetUserId() != UserId)))
            {
                TempData["message"] = "Nu există cartea cu ID-ul egal cu " + id + " !";
                return View("~/Views/Shared/NoRight.cshtml");
            }

            ViewBag.comments = db.BookComments.Where(bk => bk.BookId == id);
            foreach (var elem in db.BookComments.Where(bk => bk.BookId == id).ToList())
            {
                var x = elem.User.UserName;
            }

            return View(book);
        }

        [Authorize(Roles = "Colaborator,Administrator")]
        public ActionResult New()
        {
            return View();
        }

        [HttpPost]
        [Authorize(Roles = "Colaborator,Administrator")]
        public ActionResult New(Book book)
        {
            try
            {
                if (ModelState.IsValid)
                {
                    book.Status = (User.IsInRole("Colaborator")) ? 0 : 1;
                    db.Books.Add(book);
                    db.SaveChanges();

                    if (User.IsInRole("Colaborator"))
                    {
                        PartnerRequirement requirement = new PartnerRequirement();
                        requirement.Cantitate = 0;
                        requirement.Status = 0;
                        requirement.BookId = book.BookId;
                        requirement.Book = book;
                        requirement.UserId = User.Identity.GetUserId();
                        requirement.User = db.Users.Find(User.Identity.GetUserId());

                        db.PartnerRequirements.Add(requirement);
                        db.SaveChanges();
                    }
                    TempData["succes"] = true;
                    TempData["message"] = "Cartea a fost adaugata cu succes !";
                    return RedirectToAction("Index");
                }
                else
                {
                    return View();
                }
            }
            catch (Exception e)
            {
                TempData["message"] = "Excepție: " + e.Message;
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }

        [Authorize(Roles = "Colaborator,Administrator")]
        public ActionResult Edit(int id)
        {
            Book book = db.Books.Find(id);
            if (book == null)
            {
                TempData["message"] = "Nu există cartea cu ID-ul egal cu " + id + " !";
                return View("~/Views/Shared/NoRight.cshtml");
            }

            // Extragem idul colaboratorului care a creat cartea adică cel care a facut request-ul pt carte cu cantitate 0
            var BookPartnerRequirements = db.PartnerRequirements.Where(pr => pr.BookId == id && pr.Cantitate == 0);
            var UserId = (BookPartnerRequirements.Count() != 0) ? BookPartnerRequirements.First().UserId : "";

            if (User.IsInRole("Administrator") || User.Identity.GetUserId() == UserId)
            {
                return View(book);
            }
            else
            {
                TempData["message"] = "Nu aveți dreptul de a edita cartea !";
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }

        [HttpPut]
        [Authorize(Roles = "Colaborator,Administrator")]
        public ActionResult Edit(int id, Book requestBook)
        {
            try
            {
                Book book = db.Books.Find(id);

                // Extragem idul colaboratorului care a creat cartea adică cel care a facut request-ul pt carte cu cantitate 0
                var BookPartnerRequirements = db.PartnerRequirements.Where(pr => pr.BookId == book.BookId && pr.Cantitate == 0);
                var UserId = (BookPartnerRequirements.Count() != 0) ? BookPartnerRequirements.First().UserId : "";

                if (ModelState.IsValid)
                {
                    if (User.IsInRole("Administrator") || User.Identity.GetUserId() == UserId)
                    {
                        if (TryUpdateModel(book))
                        {
                            book.Titlu = requestBook.Titlu;
                            book.Autor = requestBook.Autor;
                            book.Descriere = requestBook.Descriere;
                            book.Editura = requestBook.Descriere;
                            book.Fotografie = requestBook.Fotografie;
                            book.Pret = requestBook.Pret;

                            db.SaveChanges();
                        }
                        TempData["succes"] = true;
                        TempData["message"] = "Cartea a fost modificată cu succes !";
                        return RedirectToAction("Index");
                    }
                    else
                    {
                        TempData["message"] = "Nu aveți dreptul de a modifica cartea !";
                        return View("~/Views/Shared/NoRight.cshtml");
                    }
                }
                else
                {
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
        [Authorize(Roles = "Colaborator,Administrator")]
        public ActionResult Delete(int id)
        {
            Book book = db.Books.Find(id);
            if (book == null)
            {
                TempData["message"] = "Nu există cartea cu ID-ul egal cu " + id + " !";
                return Content("Nu există cartea cu ID-ul egal cu " + id + " !");
            }

            // Extragem idul colaboratorului care a creat cartea adică cel care a facut request-ul pt carte cu cantitate 0
            var BookPartnerRequirements = db.PartnerRequirements.Where(pr => pr.BookId == book.BookId && pr.Cantitate == 0);
            var UserId = (BookPartnerRequirements.Count() != 0) ? BookPartnerRequirements.First().UserId : "";

            if (User.IsInRole("Administrator") || User.Identity.GetUserId() == UserId)
            {
                TempData["message"] = "Cartea a fost ștearsă!";
                db.Books.Remove(book);
                db.SaveChanges();
                return Content("Cartea a fost ștearsă!");
            }
            else
            {
                TempData["message"] = "Nu aveți dreptul de a șterge cartea!";
                return Content("Nu aveți dreptul de a șterge cartea!");
            }
        }
    }
}