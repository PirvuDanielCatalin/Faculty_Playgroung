using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using TrueJobs;

namespace TrueJobs.Controllers
{
    public class WriteCVsController : Controller
    {
        private JobsEntities2 db = new JobsEntities2();

        // GET: WriteCVs
        public ActionResult Index()
        {
            var writeCVs = db.WriteCVs.Include(w => w.User);
            return View(writeCVs.ToList());
        }

        // GET: WriteCVs/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            WriteCV writeCV = db.WriteCVs.Find(id);
            if (writeCV == null)
            {
                return HttpNotFound();
            }
            return View(writeCV);
        }

        // GET: WriteCVs/Create
        public ActionResult Create()
        {
            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName");
            return View();
        }

        // POST: WriteCVs/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "WritecvID,User_ID,PreviousWork,Email,Skills,Studies,Languages,Hobbies")] WriteCV writeCV)
        {

            // User user = db.Users.Find(User.Identity.Name.TrimEnd());

            User user = db.Users.FirstOrDefault(s => s.Email == User.Identity.Name.TrimEnd());
            if (ModelState.IsValid)
            {
                writeCV.User_ID = user.User_ID;
                writeCV.Email = User.Identity.Name.TrimEnd();

                db.WriteCVs.Add(writeCV);
                db.SaveChanges();
                //return RedirectToAction("Index");
                return RedirectToAction("Details", "Users", new { email = User.Identity.Name.TrimEnd()});
            }

            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName", writeCV.User_ID);
            return View(writeCV);
        }

        // GET: WriteCVs/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            WriteCV writeCV = db.WriteCVs.Find(id);
            if (writeCV == null)
            {
                return HttpNotFound();
            }
            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName", writeCV.User_ID);
            return View(writeCV);
        }

        // POST: WriteCVs/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "WritecvID,User_ID,PreviousWork,Email,Skills,Studies,Languages,Hobbies")] WriteCV writeCV)
        {
            if (ModelState.IsValid)
            {
                db.Entry(writeCV).State = EntityState.Modified;
                db.SaveChanges();
                //return RedirectToAction("Index");
                return RedirectToAction("Details", "WriteCVs", new { id = writeCV.WritecvID});
            }
            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName", writeCV.User_ID);
            return View(writeCV);
        }

        // GET: WriteCVs/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            WriteCV writeCV = db.WriteCVs.Find(id);
            if (writeCV == null)
            {
                return HttpNotFound();
            }
            return View(writeCV);
        }

        // POST: WriteCVs/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            WriteCV writeCV = db.WriteCVs.Find(id);
            db.WriteCVs.Remove(writeCV);
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
