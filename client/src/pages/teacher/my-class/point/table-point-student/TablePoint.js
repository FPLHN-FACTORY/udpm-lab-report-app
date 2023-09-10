import { Input, Table } from "antd";
import { useAppSelector } from "../../../../../app/hook";
import { GetPoint } from "../../../../../app/teacher/point/tePointSlice.reduce";

const TablePoint = () => {
  const dataSource = useAppSelector(GetPoint);
  const columns = [
    {
      title: "#",
      dataIndex: "stt",
      key: "stt",
      render: (text, record, index) => <span>{index + 1}</span>,
    },
    {
      title: "Tên sinh viên",
      dataIndex: "name",
      key: "name",
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email",
    },
    {
      title: "Điểm giai đoạn 1",
      dataIndex: "checkPointPhase1",
      key: "checkPointPhase1",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              readOnly
              onDoubleClick={(e) => {
                e.target.readOnly = false;
                e.target.select();
              }}
              onBlur={(e) => {
                e.target.readOnly = true;
              }}
              onChange={(e) => {
                //  setName(e.target.value);
                // onChange={(e) => {
                //handleAddressChange(record.idMeeting, e.target.value);
              }}
              type="number"
            />
          </>
        );
      },
    },
    {
      title: "Điểm giai đoạn 2",
      dataIndex: "checkPointPhase2",
      key: "checkPointPhase2",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              onChange={(e) => {
                //  setName(e.target.value);
              }}
              type="number"
            />
          </>
        );
      },
    },
    {
      title: "Điểm final",
      dataIndex: "finalPoint",
      key: "finalPoint",
      render: (text, record) => {
        return (
          <>
            {" "}
            <Input
              placeholder="Nhập điểm"
              value={text}
              onChange={(e) => {
                //  setName(e.target.value);
              }}
              type="number"
            />
          </>
        );
      },
    },
  ];
  return (
    <>
      <Table
        rowKey="id"
        columns={columns}
        dataSource={dataSource}
        pagination={false}
      />
    </>
  );
};
export default TablePoint;
