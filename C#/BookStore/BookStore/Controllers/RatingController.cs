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
    public class RatingController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        [HttpPost]
        public ActionResult NewOrEdit(Rating requestRating)
        {
            try
            {
                var UserId = User.Identity.GetUserId(); // Id user logat
                // Testam daca userul a mai evaluat cartea
                var result = db.Ratings.Where(r => r.BookId == requestRating.BookId && r.UserId == UserId).ToList();
                if (result.Count == 0)
                {
                    Rating rating = requestRating;
                    rating.Book = db.Books.Find(rating.BookId);
                    rating.UserId = UserId;
                    rating.User = db.Users.Find(UserId);

                    if (ModelState.IsValid)
                    {
                        db.Ratings.Add(rating);
                        db.SaveChanges();
                        TempData["message"] = "Cartea a fost evaluată !";
                        return Content("Succes");
                    }
                    else
                    {
                        return Content("Error");
                    }
                }
                else
                {
                    Rating rating = result.First();
                    if (ModelState.IsValid)
                    {
                        if (TryUpdateModel(rating))
                        {
                            rating.Value = requestRating.Value;
                            db.SaveChanges();
                        }
                        return Content("Succes");
                    }
                    else
                    {
                        return Content("Error");
                    }
                }
            }
            catch (Exception e)
            {
                TempData["message"] = "Excepție: " + e.Message;
                return View("~/Views/Shared/NoRight.cshtml");
            }
        }
    }
}