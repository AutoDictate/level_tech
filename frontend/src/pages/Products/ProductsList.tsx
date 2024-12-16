import { Autocomplete, Box, Button, IconButton, TextField, Typography } from "@mui/material";
import CustomTable from "../../components/CustomTable";
import { useDeleteMasterProductMutation, useGetAllMasterProductQuery } from "../../features/MasterProductService/MasterProductApi";
import CustomSearchInput from "../../components/CustomSearchInput";
import { Delete } from "@mui/icons-material";
import EditIcon from '@mui/icons-material/Edit';
import { useDeleteProductMutation, useUpdateProductMutation } from "../../features/ProductService/ProductApi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useState } from "react";
import DatePicker from "../../components/DatePicker";

const ProductsList = () => {
  const navigate = useNavigate();
  const [searchInput, setSearchInput] = useState("")
  const [filterBy, setFilterBy] = useState({ label: '', value: "" })
  const [filterFromDate, setFilterFromDate] = useState(undefined)
  const [filterToDate, setFilterToDate] = useState(undefined)
  // Fetch the product data from the backend
  const { data, refetch } = useGetAllMasterProductQuery({}, { refetchOnMountOrArgChange: true });
  const [deleteMasterProduct] = useDeleteMasterProductMutation()

  const handleEdit = (id: number) => {
    navigate(`edit/${id}`)
  }
  const handleDelete = (id: number) => {
    console.log(id);
    deleteMasterProduct([id]).then(res => {
      if (res?.error) {
        toast.error("Unable to delete");
      }
      if (res?.data) {
        toast.success("Product deleted successfully");
        refetch();
      }
    })

  }

  console.log(data?.length);


  // Map the data to extract categoryName, productName, and modelName dynamically
  const productRows = data?.map((item: any) => ({
    id: item.id,
    categoryName: item.category.name,
    productName: item.product.name,
    modelName: item.model.name,
    hsnCode: item.hsnAcs,
    tax: item.tax,
    update: item.lastModifiedAt,
    action: <Box><IconButton size="small" onClick={() => handleEdit(item.id)}><EditIcon /></IconButton><IconButton size="small" sx={{ color: "red" }} onClick={() => handleDelete(item.id)}><Delete /></IconButton></Box>
  })) || [];

  // Define the columns dynamically
  const columns = [
    { id: 'categoryName', label: 'Category Name' },
    { id: 'productName', label: 'Product Name' },
    { id: 'modelName', label: 'Model Name' },
    { id: 'hsnCode', label: 'HSN' },
    { id: 'tax', label: 'Tax' },
    { id: 'update', label: 'Updated' },
    { id: 'action', label: 'Action' },
  ];

  const handleSubmit = (values: any) => {
    console.log(values)
  }

  const handleFilterByTypeChange = (e: any, value: any) => {
    setFilterBy(value)
  }
  const handleFilterFromDateChange = (date: any) => {
    setFilterFromDate(date)
  }
  const handleFilterTomDateChange = (date: any) => {
    setFilterToDate(date)
  }
  const handleFilter = () => {
    console.log(filterBy, filterFromDate, filterToDate);

  }

  const filterOptions = [
    { label: 'Created', value: "created" },
    { label: 'Date', value: "date" },
    { label: 'Bill Date', value: "bill date" },
    { label: 'Purchase Date', value: "purchase date" },
  ]

  const handleLastMonth = () => {
    const getLastMonth = new Date();
    let day = getLastMonth.getDate();
    let month = getLastMonth.getMonth()-1;
    let year = getLastMonth.getFullYear();
    let date = `${year}-${month}-${day}`;
    console.log(getLastMonth.getMonth() - 1);
    console.log(date);
    


  }
  const handleCurrentMonth = () => {
    const getCurrentMonth = new Date();
    let day = getCurrentMonth.getDate();
    let month = getCurrentMonth.getMonth();
    let year = getCurrentMonth.getFullYear();
    let date = `${year}-${month}-${day}`;
    console.log(getCurrentMonth.getDate());
    console.log(date);
    

  }

  const handleSearchInput = (e: any) => {
    setSearchInput(e.target.value)
    console.log(e.target.value);

  }

  return (
    <>
      <Box mt={3}>
        <Box display={'flex'} justifyContent={"space-between"} pb={2}>
          <Box>
            <Button onClick={handleLastMonth}>LAST MONTH</Button>
            <Button onClick={handleCurrentMonth}>CURRENT MONTH</Button>
          </Box>
          <Box display={'flex'}>
            <Autocomplete options={filterOptions} getOptionLabel={(option) => option.label || ""} renderInput={(params) => <TextField {...params} placeholder='Filter By' />} sx={{ width: 120, pr: 2 }} size='small' onChange={(e, value) => handleFilterByTypeChange(e, value)} value={filterBy} />
            <Box pr={2}>
              <DatePicker handleOnChange={(date) => handleFilterFromDateChange(date)} label='' name='Date' value={filterFromDate} placeholder='From' />
            </Box>
            <Box pr={2}>
              <DatePicker handleOnChange={(date) => handleFilterTomDateChange(date)} label='' name='Date' value={filterToDate} placeholder='To' />
            </Box>
            <Button variant='contained' onClick={handleFilter}>Filter</Button>
          </Box>
        </Box>
        {/* Pass the columns and rows dynamically to the CustomTable */}
        <Box display={"flex"} justifyContent={"space-between"}>
          <Typography sx={{ fontWeight: 600, fontSize: 20 }}>Products ({data?.length})</Typography>
          <Box display={"flex"} justifyContent={"end"}>

            <CustomSearchInput value={searchInput} handleChange={handleSearchInput} />

            <Button onClick={() => navigate("new-product")} variant="contained" sx={{ bgcolor: "#014", width: 150, height: 55 }}>
              Add Product
            </Button>
          </Box>
        </Box>
        <CustomTable columns={columns} rows={productRows} />
        <Typography sx={{ color: "grey", mt: 2 }}>Total available items : {data?.length}</Typography>
      </Box>
    </>
  );
};

export default ProductsList;
