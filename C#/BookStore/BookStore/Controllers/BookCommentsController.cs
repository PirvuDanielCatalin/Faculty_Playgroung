using Booktopia.Models;
using Microsoft.AspNet.Identity;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Booktopia.Controllers
{
    public class BookCommentsController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        [HttpPost]
        [Authorize(Roles = "User,Colaborator,Administrator")]
        public ActionResult New(BookComment bookComment)
        {
            bookComment.Likes = 0;
            bookComment.DataAprobare = DateTime.Now;
            bookComment.Book = db.Books.Find(bookComment.BookId);
            bookComment.UserId = User.Identity.GetUserId();
            bookComment.User = db.Users.Find(bookComment.UserId);

            try
            {
                if (ModelState.IsValid)
                {
                    db.BookComments.Add(bookComment);
                    db.SaveChanges();
                    TempData["message"] = "Comentariul a fost adăugat!";
                    return Content("Succes");
                }
                else
                {
                    return Content("Error");
                }
            }
            catch (Exception e)
            {
                TempData["message"] = "Excepție: " + e.Message;
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }

        [HttpPut]
        public ActionResult Edit(int id, BookComment requestBookComment)
        {
            try
            {
                BookComment bookComment = db.BookComments.Find(id);
                if (ModelState.IsValid)
                {
                    var UserId = User.Identity.GetUserId();
                    if (User.IsInRole("Administrator") || UserId == bookComment.UserId)
                    {
                        if (TryUpdateModel(bookComment))
                        {
                            bookComment.Comentariu = requestBookComment.Comentariu;
                            bookComment.DataAprobare = DateTime.Now;
                            db.SaveChanges();
                        }
                        return Content("Succes");
                    }
                    else
                    {
                        return Content("NoRight");
                    }
                }
                else
                {
                    return Content("Error");
                }
            }
            catch (Exception e)
            {
                return Content("Exception:" + e.Message);
            }
        }

        [HttpDelete]
        [Authorize(Roles = "User,Colaborator,Administrator")]
        public ActionResult Delete(int id)
        {
            BookComment bookComment = db.BookComments.Find(id);
            if (User.IsInRole("Administrator") || User.Identity.GetUserId() == bookComment.UserId)
            {
                TempData["message"] = "Comentariul a fost șters !";
                db.BookComments.Remove(bookComment);
                db.SaveChanges();
                return Content("Succes");
            }
            else
            {
                return Content("NoRight");
            }

        }
    }
}