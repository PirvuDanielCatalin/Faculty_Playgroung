using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class PartnerRequirement
    {
        [Key]
        public int IdCerere { get; set; }

        public int Cantitate { get; set; }

        public int Status { get; set; } // 0 - In asteptarea unui raspuns, 1 - Acceptata, -1 - Respinsa

        public int BookId { get; set; }
        public string UserId { get; set; }

        // Relatii
        public virtual Book Book { get; set; }
        public virtual ApplicationUser User { get; set; }

        // Defaults
        public PartnerRequirement()
        {
            this.Cantitate = 0;
            this.Status = 0;
        }
    }
}