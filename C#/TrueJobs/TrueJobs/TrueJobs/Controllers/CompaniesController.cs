using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using TrueJobs;


using PagedList;

namespace TrueJobs.Controllers
{
    public class CompaniesController : Controller
    {
        private JobsEntities2 db = new JobsEntities2();

        // GET: Companies
        public ActionResult Index()
        {


            return View(db.Companies.ToList());
        }

        // GET: Companies/Details/5
        public ActionResult Details(/*int? id*/string email)
        {
            if (email == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            
            Company company = db.Companies.SingleOrDefault(d => d.Email == email);
            ViewBag.compId = company.Company_ID;

            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        // GET: Companies/Create
        public ActionResult Create()
        {
            return View();
        }

        // POST: Companies/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [ValidateInput(false)]
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create( Company company, HttpPostedFileBase file2)
        {
            if (file2 != null && file2.ContentLength > 0)
            {
                // extract only the filename
                var fileName = Path.GetFileNameWithoutExtension(file2.FileName) + " _ " + DateTime.Now.ToString("ddHmmss") + Path.GetExtension(file2.FileName);
                var path = Path.Combine(Server.MapPath("~/imgpoze"), fileName);
                company.Photo = fileName;
                file2.SaveAs(path);



            }

            if (ModelState.IsValid)
            {
                company.Email = User.Identity.Name.TrimEnd();
                db.Companies.Add(company);
                db.SaveChanges();
                return RedirectToAction("Details", new { email = company.Email });
            }

            return View(company);
        }

        // GET: Companies/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Company company = db.Companies.Find(id);
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        // POST: Companies/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(HttpPostedFileBase file2, Company company)
        {
            if (ModelState.IsValid)
            {

                if (file2 != null && file2.ContentLength > 0)
                {
                    // extract only the filename
                    var fileName = Path.GetFileNameWithoutExtension(file2.FileName) + " _ " + DateTime.Now.ToString("ddHmmss") + Path.GetExtension(file2.FileName);
                    var path = Path.Combine(Server.MapPath("~/imgpoze"), fileName);
                    company.Photo = fileName;
                    file2.SaveAs(path);



                }

                db.Entry(company).State = EntityState.Modified;
                db.SaveChanges();
                //return RedirectToAction("Index");
                return RedirectToAction("Details", new { email = company.Email });

            }
            return View(company);
        }

        // GET: Companies/Delete/5
        public ActionResult Delete(/*int? id*/string email)
        {
            if (email == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Company company = db.Companies.SingleOrDefault(d => d.Email == email);
            if (company == null)
            {
                return HttpNotFound();
            }
            return View(company);
        }

        // POST: Companies/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Company company = db.Companies.Find(id);
            db.Companies.Remove(company);
            db.SaveChanges();
            return RedirectToAction("Index");
        }


        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }
    }
}
