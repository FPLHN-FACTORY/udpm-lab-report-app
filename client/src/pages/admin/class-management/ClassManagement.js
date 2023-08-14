import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faFilter,
  faCogs,
  faPencil,
  faPlus
} from "@fortawesome/free-solid-svg-icons";
import { useAppDispatch, useAppSelector } from "../../../app/hook";
import {
  Button,
  Input,
  Pagination,
  Table,
  Tooltip,
  Select
} from "antd";
import LoadingIndicator from "../../../helper/loading";
import { SetTeacherSemester } from "../../../app/admin/ClassManager.reducer";

import { useEffect, useState } from "react";
import { ClassAPI } from "../../../api/admin/class-manager/ClassAPI.api";
import "./style-class-management.css";
import { list } from "@chakra-ui/react";


const ClassManagement = () => {
  const { Option } = Select;
  const [listClassAll, setlistClassAll] = useState([]); //getAll
  const [teacherDataAll, setTeacherDataAll] = useState([]); // Dữ liệu teacherId và username
  const [semesterDataAll, setSemesterDataAll] = useState([]); // Dữ liệu semester
  const [activityDataAll, setActivityDataAll] = useState([]); // Dữ liệu activity
  const [idSemesterSeach, setIdSemesterSearch] = useState("");
  const [idActivitiSearch, setIdActivitiSearch] = useState("");

  const [selectedItems, setSelectedItems] = useState([]);
  const [code, setCode] = useState('');
  const [selectedItemsPerson, setSelectedItemsPerson] = useState('');
  const [filteredData, setFilteredData] = useState([...listClassAll]);
  const [loading, setLoading] = useState(true);

  const dispatch = useAppDispatch();

  useEffect(() => {
    fetchData(idSemesterSeach)
  }, [idSemesterSeach]);
  
  useEffect(() => {
    // Lấy dữ liệu teacherId và username từ mock API
    const fetchTeacherData = async () => {
      const responseTeacherData = await ClassAPI.fetchAllTeacher();
      const teacherData = responseTeacherData.data;
      setTeacherDataAll(teacherData);
    };
    fetchTeacherData();
  }, []);
  useEffect(() => {
    const featchDataActivity = async (idSemesterSeach) => {
      console.log(idSemesterSeach);
        await ClassAPI.getAllActivityByIdSemester(idSemesterSeach).then(
          (respone) => {
            setActivityDataAll(respone.data.data);
            setLoading(true);
          }
        );

    };
    featchDataActivity(idSemesterSeach);
  }, [idSemesterSeach]);
  useEffect(() => {
    const featchDataSemester = async () => {
      // try {
        setLoading(false);
        const responseClassAll =
        await ClassAPI.fetchAllSemester();
      const listClassAll = responseClassAll.data;
          dispatch(SetTeacherSemester(listClassAll.data));
          if (listClassAll.data.length > 0) {
            setIdSemesterSearch(listClassAll.data[0].id);
          } else {
            setIdSemesterSearch("null");
          }
          setSemesterDataAll(listClassAll.data);
          setLoading(true);
    
      // } catch (error) {
      //   alert("Vui lòng F5 lại trang !");
      // }
    };
    featchDataSemester();
  }, []);
  
  useEffect(() => {
    // document.title = "Quản lý dự án | Portal-Projects";
    setCode("");
    setSelectedItems("");
    setSelectedItemsPerson("");
    // fetchDataByCondition();

  }, []);

  const fetchData = async (idSemesterSeach) => {

    const responseClassAll =
        await ClassAPI.getAllMyClassByIdSemester(idSemesterSeach);
      const listClassAll = responseClassAll.data;
      setlistClassAll(listClassAll.data); //All Project
      
  }
  const columns = [
    {
      title: "STT",
      dataIndex: "id",
      key: "id",
      sorter: (a, b) => a.stt.localeCompare(b.stt),
      render: (text, record, index) => index + 1,
    },
    {
      title: "Mã",
      dataIndex: "code",
      key: "code",
      sorter: (a, b) => a.code.localeCompare(b.code),
    },
    {
      title: "Tên Lớp",
      dataIndex: "name",
      key: "name",
      sorter: (a, b) => a.username.localeCompare(b.username),
    },
    {
      title: "Thời gian bắt đầu",
      dataIndex: "startTime",
      key: "startTime",
      sorter: (a, b) => a.startTime.localeCompare(b.startTime),
    },
    {
      title: "Ca học",
      dataIndex: "classPeriod",
      key: "classPeriod",
      sorter: (a, b) => a.classPeriod.localeCompare(b.classPeriod),
    },
    {
      title: "Sĩ số",
      dataIndex: "classSize",
      key: "classSize",
      sorter: (a, b) => a.classSize.localeCompare(b.classSize),
    },
    {
      title: "Giáo viên",
      dataIndex: "teacherId",
      key: "teacherId",
      sorter: (a, b) => a.teacherId.localeCompare(b.teacherId),
      render: (text, record) => {
        const teacher = teacherDataAll.find((item) => item.id === record.teacherId);
        return teacher ? teacher.username : "";
      },
    },
    {
      title: "Hoạt động",
      dataIndex: "activityName",
      key: "activityName",
      sorter: (a, b) => a.activityName.localeCompare(b.activityName),
    },
    {
      title: "Hành động",
      dataIndex: "actions",
      key: "actions",
      
      // render: (text,record) => (
      //   <div>
      //     <Tooltip title="Cập nhật">
      //       <FontAwesomeIcon
      //         onClick={() => {
      //           handlePeriodUpdate(record.id);
      //         }}
      //         style={{ marginRight: "15px", cursor: "pointer" }}
      //         icon={faPencil}
      //         size="1x"
      //       />
      //     </Tooltip>
      //   </div>
      // ),
    },
  ];
  
  

const handleCodeChange = (e) => {
  setCode(e.target.value);
};
const handleSelectChange = (value) => {
  setSelectedItems(value);
};

const handleSelectPersonChange = (value) => {
  setSelectedItemsPerson(value);
};

useEffect(() => {

  handleSearch(idSemesterSeach);
  
}, [code, selectedItems, selectedItemsPerson,idActivitiSearch,idSemesterSeach]);
const handleSearch =async (idSemesterSeach) => {

  if (listClassAll.length===0){
    const responseClassAll =
        await ClassAPI.getAllMyClassByIdSemester(idSemesterSeach);
      const listClass = responseClassAll.data;
      
    setFilteredData([...listClass.data]);
    console.log(listClass);
  } else {
  const filteredList = listClassAll.filter((classItem) => {
    const codeMatch = classItem.code.includes(code);
    const periodMatch = classItem.classPeriod === selectedItems;
    const teacherMatch = classItem.teacherId === selectedItemsPerson;
    const activityMatch = classItem.activityName === idActivitiSearch;
    const semesterMatch = classItem.semesterName === idSemesterSeach;

    if (code && selectedItems && selectedItemsPerson && idActivitiSearch && idSemesterSeach) {
      return codeMatch && periodMatch && teacherMatch && activityMatch && semesterMatch;
    } else if (code && selectedItems) {
      return codeMatch && periodMatch;
    } else if (code && selectedItemsPerson) {
      return codeMatch && teacherMatch;
    }else if (code && idActivitiSearch) {
      return codeMatch && activityMatch;
    } else if (code && idSemesterSeach) {
      return codeMatch && semesterMatch;
    } else if (selectedItems && selectedItemsPerson) {
      return periodMatch && teacherMatch;
    }else if (selectedItems && idActivitiSearch) {
      return periodMatch && activityMatch;
    }else if (selectedItems && idSemesterSeach) {
      return periodMatch && semesterMatch;
    }else if (selectedItemsPerson && idActivitiSearch) {
      return activityMatch && teacherMatch;
    }else if (selectedItemsPerson && idSemesterSeach) {
      return activityMatch && semesterMatch;
    }else if (idSemesterSeach && idActivitiSearch) {
      return activityMatch && semesterMatch;
    } else if (code) {
      return codeMatch;
    } else if (selectedItems) {
      return periodMatch;
    } else if (selectedItemsPerson) {
      return teacherMatch;
    } else if (idActivitiSearch) {
      return activityMatch;
    } else if (idSemesterSeach) {
      return semesterMatch;
    } else {
      return true; // Nếu không có bất kỳ điều kiện nào được chọn, hiển thị tất cả lớp
    }
  });

  setFilteredData([...filteredList]);
}
};

  return (
    <div className="stakeholder_management">
            {/* {loading && <LoadingIndicator />} */}

      <div className="title_stakeholder_management">
        {" "}
        <FontAwesomeIcon icon={faCogs} size="1x" />
        <span style={{ marginLeft: "10px" }}>Quản lý lớp</span>
      </div>
      <div className="filter">
        <FontAwesomeIcon icon={faFilter} size="2x" />{" "}
        <span style={{ fontSize: "18px", fontWeight: "500" }}>Bộ lọc</span>
        <hr />
        <div className="content">
          <div className="content-wrapper">
            <div className="inputCode">
            Mã Lớp:{" "}
              <Input
                placeholder="Import Class Code"
                type="text"
                value={code}
                // onChange={handleInputChange}
                onChange={handleCodeChange}

                style={{ width: "220px" }}
              />
            </div>
            <div className="selectSearch1">
            Ca Học:{" "}
              <Select
                  showSearch
                  placeholder="Select a class Period"
                  value={selectedItems}
                  onChange={handleSelectChange}
                  style={{ width: '220px' , height: '50%',marginRight:'110px'}}
                  >
                      <Option value="">Tất Cả</Option>
                     {listClassAll.map((classItem) => (
                    <Option key={classItem.id} value={classItem.classPeriod}>
                      {classItem.classPeriod}
                    </Option>
                       ))}
              </Select>
            </div>
            <div className="selectSearch2" >
            GVHD:{" "}
              <Select
                  showSearch
                  placeholder="Select a person "
                  value={selectedItemsPerson}
                  onChange={handleSelectPersonChange}
                style={{ width: '220px', height: '50%', marginRight: '210px' }}
                   >

                      <Option value="">Tất cả</Option>

                {teacherDataAll.map((teacher) => (
                  <Option key={teacher.id} value={teacher.id}>
                    {teacher.username}
                  </Option>
                ))}
              </Select>
                        </div>
          </div>
        </div>
        
        
      </div>

      <div className="table_Allstakeholder_management">
        <div className="title_stakeholder_management">
          {" "}
          <FontAwesomeIcon icon={faCogs} size="1x" />
          <span style={{ fontSize: "18px", fontWeight: "500" }}>
            {" "}
            Danh sách Lớp
          </span>
        </div>
        Semester:{" "}
              <Select
                  showSearch
                  placeholder="Select a person "
                  value={idSemesterSeach}
                  onChange={(value) => {
                    setIdSemesterSearch(value);
                  }}
                style={{ width: '220px', height: '50%', marginRight: '210px' }}
                   >

                      <Option value="">Tất cả</Option>

                {semesterDataAll.map((semester) => (
                  <Option key={semester.id} value={semester.id}>
                    {semester.name}
                  </Option>
                ))}
              </Select>
        Hoạt Động:{" "}
              <Select
                  showSearch
                  placeholder="Select a activity "
                  value={idActivitiSearch}
                  onChange={(value) => {
                    setIdActivitiSearch(value);
                  }}
                style={{ width: '220px', height: '50%', marginRight: '210px' }}
              >
                      <Option value="">Tất cả</Option>
                        {activityDataAll.map((activity) => (
                      <Option key={activity.name} value={activity.name}>
                               {activity.name}
                      </Option>
                ))}
              </Select>
        <div style={{ marginTop: "25px" }}>
          <Table
            dataSource={filteredData}

            rowKey="id"
            columns={columns}
            className="table_stakeholder_management"
            pagination={{
              pageSize: 3,
              showSizeChanger: false,
              size: "small",
            }}
          />
        </div>
        <div>
            <Button
              style={{
                color: "white",
                backgroundColor: "rgb(55, 137, 220)",
              }}
              // onClick={buttonCreate}
            >
              <FontAwesomeIcon
                icon={faPlus}
                size="1x"
                style={{
                  backgroundColor: "rgb(55, 137, 220)",
                }}
              />{" "}
              Thêm Lớp
            </Button>
          </div>
          
      </div>
      {/* <ModalCreateProject
          visible={showCreateModal}
          onCancel={handleCancelCreate}
        /> */}
    </div>
  );
};

export default ClassManagement;
