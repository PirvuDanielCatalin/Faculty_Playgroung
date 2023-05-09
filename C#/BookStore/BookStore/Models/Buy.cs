using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class Buy
    {
        [Key]
        public int BuyId { get; set; }

        public int BookId { get; set; }
        public string UserId { get; set; }
        public int InvoiceId { get; set; }

        // Relatii
        public virtual Book Book { get; set; }
        public virtual ApplicationUser User { get; set; }
        public virtual Invoice Invoice { get; set; }

    }
}