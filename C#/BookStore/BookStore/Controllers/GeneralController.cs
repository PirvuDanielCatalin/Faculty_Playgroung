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
    public class GeneralController : Controller
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();

        [Authorize(Roles = "Administrator,Colaborator")]
        public ActionResult Index()
        {
            return View();
        }

        [Authorize(Roles = "Administrator")]
        public ActionResult BookManagement()
        {
            ViewBag.books = db.Books;
            return View();
        }

        [Authorize]
        public ActionResult ShoppingCart()
        {
            return View();
        }

        [HttpPost]
        public string GetBookInfo(int id)
        {
            Book book = db.Books.Find(id);
            var data = "{ 'id':'" + book.BookId +
                       "','title':'" + book.Titlu +
                       "','autor':'" + book.Autor +
                       "','editura':'" + book.Editura +
                       "','descriere':'" + book.Descriere +
                       "','pret':'" + book.Pret + "' }";
            return data;
        }
    }
}