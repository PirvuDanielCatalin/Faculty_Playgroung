using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Helpers;
using System.Web.Mvc;
using TrueJobs;

namespace TrueJobs.Controllers
{
    public class ApplicationsController : Controller
    {
        private JobsEntities2 db = new JobsEntities2();

        // GET: Applications
        public ActionResult Index()
        {
            

             var applications = db.Applications.Include(a => a.User).Include(a => a.Job);
            
            return View(applications.ToList());
        }

        public ActionResult AplicarileMele(int id)
        {
            var app = db.Applications.Where(a => a.User_ID == id);

            return View(app.ToList());
        }


        public ActionResult SendEmail(string email, string jb)
        {


            if (ModelState.IsValid)
            {
                /*
                app.Job_ID = id;
                User user = db.Users.SingleOrDefault(s => s.Email == User.Identity.Name.TrimEnd());
                app.User_ID = user.User_ID;

                db.Applications.Add(app);
                db.SaveChanges();



                Job job = db.Jobs.SingleOrDefault(s => s.Job_ID == id);
                Company comp = db.Companies.SingleOrDefault(s => s.Company_ID == job.Company_ID);
                */

                string body = "Esti chemat la interviu pentru jobul " + jb+"!";
                string Body = "Esti chemat la interviu pentru jobul " + jb+ "!";
                string subject = "Invitatie la interviu";
                string Subject = "Invitatie la interviu";


                WebMail.SmtpServer = "smtp.gmail.com";
                //gmail port to send emails  
                WebMail.SmtpPort = 587;
                WebMail.SmtpUseDefaultCredentials = true;
                //sending emails with secure protocol  
                WebMail.EnableSsl = true;
                //EmailId used to send emails from application  
                WebMail.UserName = "truejobsproiect@gmail.com";
                WebMail.Password = "truejobsproiect@1234..";

                //Sender email address.  
                WebMail.From = "truejobsproiect@gmail.com";

                //Send email  
                WebMail.Send(to: email, subject: subject, body: body, cc: "", bcc: "", isBodyHtml: true);
                ViewBag.Status = "Email Sent Successfully.";

            }






            return Content("<script language='javascript' type='text/javascript'>alert('Ai trimis emailul'); window.location.href = 'http://localhost:50961/Jobs/Index/' ;</script>");
        }


        // GET: Applications/Details/5
        public ActionResult Details(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Application application = db.Applications.Find(id);
            if (application == null)
            {
                return HttpNotFound();
            }
            return View(application);
        }

        // GET: Applications/Create
        public ActionResult Create()
        {
            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName");
            ViewBag.Job_ID = new SelectList(db.Jobs, "Job_ID", "Name");
            return View();
        }

        // POST: Applications/Create
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Create([Bind(Include = "Application_ID,Job_ID,User_ID")] Application application)
        {
            if (ModelState.IsValid)
            {
                db.Applications.Add(application);
                db.SaveChanges();
                return RedirectToAction("Index");
            }

            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName", application.User_ID);
            ViewBag.Job_ID = new SelectList(db.Jobs, "Job_ID", "Name", application.Job_ID);
            return View(application);
        }

        // GET: Applications/Edit/5
        public ActionResult Edit(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Application application = db.Applications.Find(id);
            if (application == null)
            {
                return HttpNotFound();
            }
            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName", application.User_ID);
            ViewBag.Job_ID = new SelectList(db.Jobs, "Job_ID", "Name", application.Job_ID);
            return View(application);
        }

        // POST: Applications/Edit/5
        // To protect from overposting attacks, please enable the specific properties you want to bind to, for 
        // more details see https://go.microsoft.com/fwlink/?LinkId=317598.
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit([Bind(Include = "Application_ID,Job_ID,User_ID")] Application application)
        {
            if (ModelState.IsValid)
            {
                db.Entry(application).State = EntityState.Modified;
                db.SaveChanges();
                return RedirectToAction("Index");
            }
            ViewBag.User_ID = new SelectList(db.Users, "User_ID", "FirstName", application.User_ID);
            ViewBag.Job_ID = new SelectList(db.Jobs, "Job_ID", "Name", application.Job_ID);
            return View(application);
        }

        // GET: Applications/Delete/5
        public ActionResult Delete(int? id)
        {
            if (id == null)
            {
                return new HttpStatusCodeResult(HttpStatusCode.BadRequest);
            }
            Application application = db.Applications.Find(id);
            if (application == null)
            {
                return HttpNotFound();
            }
            return View(application);
        }

        // POST: Applications/Delete/5
        [HttpPost, ActionName("Delete")]
        [ValidateAntiForgeryToken]
        public ActionResult DeleteConfirmed(int id)
        {
            Application application = db.Applications.Find(id);
            db.Applications.Remove(application);
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
