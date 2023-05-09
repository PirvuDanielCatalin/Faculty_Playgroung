using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace Booktopia.Models
{
    public class Stock
    {
        [Key]
        public int StockId { get; set; }

        [Required]
        public int Cantitate { get; set; }

        public int BookId { get; set; }

        // Relatii
        [Required]
        public virtual Book Book { get; set; }
    }
}