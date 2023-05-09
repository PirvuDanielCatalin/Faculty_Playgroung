using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(TrueJobs.Startup))]
namespace TrueJobs
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
