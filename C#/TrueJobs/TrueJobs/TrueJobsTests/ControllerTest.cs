using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using TrueJobs;
using TrueJobs.Controllers;
using PagedList;

using System.Net;
using System.Net.Mail;
using System.Web.Helpers;

namespace TrueJobsTests
{
    [TestClass]
    public class ControllerTest
    {
        //private JobsEntities2 db = new JobsEntities2();

        [TestMethod]
        public void Index()
        {
            /*
            //Arrange
            WriteCVsController controller = new WriteCVsController();

            //Act
            ViewResult res = controller.Index() as ViewResult;

            //Assert
            Assert.IsNotNull(res);
            */

            HomeController controller = new HomeController();
            ViewResult result = controller.Index() as ViewResult;
            Assert.IsNotNull(result);

        }

        [TestMethod]
        public void Create()
        {
            //Arrange
            /*WriteCVsController controller = new WriteCVsController();

            //Act
            ViewResult res = controller.Index() as ViewResult;

            //Assert
            Assert.IsNotNull(res);
            */

            UsersController controller = new UsersController();
            ViewResult result = controller.Create() as ViewResult;
            Assert.IsNotNull(result);

        }

   




    }
}
