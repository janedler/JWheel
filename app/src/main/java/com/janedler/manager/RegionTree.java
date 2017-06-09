package com.janedler.manager;


import com.janedler.domain.region.City;
import com.janedler.domain.region.County;
import com.janedler.domain.region.Province;
import com.janedler.manager.base.WheelNode;
import com.janedler.manager.base.WheelTree;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 继承JWheelTree进行水平扩展JWheel
 * 此类可以作为示例
 * Created by janedler on 2017/6/8.
 */
public class RegionTree extends WheelTree {

    private List<Province> mProvinces;

    public RegionTree(List<Province> provinces){
        this.mProvinces = provinces;
        super.initTree();
    }

    /**
     * buildTree必须自己实现 构建数据结构
     * @return
     */
    @Override
    public List<WheelNode> buildTree() {
        if (mProvinces == null || mProvinces.size() <= 0) return null;
        List<WheelNode> nodes = new ArrayList<>();
        for (Province province : mProvinces) {
            WheelNode provinceNode = new WheelNode(province);
            provinceNode.mNodeShowTv = province.provinceName;
            provinceNode.setParentNode(null);
            List<City> cities = province.cities;
            if (cities == null || cities.size() <= 0) continue;
            for (City city : cities) {
                WheelNode cityNode = new WheelNode(city);
                cityNode.mNodeShowTv = city.cityName;
                provinceNode.addChildNode(cityNode);
                cityNode.setParentNode(provinceNode);
                List<County> counties = city.counties;
                if (counties == null || counties.size() <= 0) continue;
                for (County county : counties) {
                    WheelNode countyNode = new WheelNode(county);
                    countyNode.mNodeShowTv = county.countyName;
                    cityNode.addChildNode(countyNode);
                    countyNode.setParentNode(cityNode);
                }
            }
            nodes.add(provinceNode);
        }
        return nodes;
    }

    /**
     * 每个节点直接的比较逻辑
     * @param node
     * @param findNode
     * @return
     */
    @Override
    public boolean compare(WheelNode node, WheelNode findNode) {
        if (node == null || findNode == null) return false;
        Object nodeObj = node.mObject;
        Object findNodeObj = findNode.mObject;
        if (nodeObj instanceof Province && findNodeObj instanceof Province){
            Province nodeProvince = (Province) nodeObj;
            Province findNodeProvince = (Province) findNodeObj;
            return isFindNode(nodeProvince.provinceId,findNodeProvince.provinceId,
                    nodeProvince.provinceName,findNodeProvince.provinceName,
                    nodeProvince.provinceCode,findNodeProvince.provinceCode);
        }
        if (nodeObj instanceof City && findNodeObj instanceof City){
            City nodeCity = (City) nodeObj;
            City findNodeCity = (City) findNodeObj;
            return isFindNode(nodeCity.cityId,findNodeCity.cityId,
                    nodeCity.cityName,findNodeCity.cityName,
                    nodeCity.cityCode,findNodeCity.cityCode);
        }
        if (nodeObj instanceof County && findNodeObj instanceof County){
            County nodeCounty = (County) nodeObj;
            County findNodeCounty = (County) findNodeObj;
            return isFindNode(nodeCounty.countyId,findNodeCounty.countyId,
                    nodeCounty.countyName,findNodeCounty.countyName,
                    nodeCounty.countyCode,findNodeCounty.countyCode);
        }
        return false;
    }

    /**
     * 返回的数据处理 lastNode是最后一个子节点 自己用最后一个子节点此次找到父节点的方法获得返回数据
     * @param lastNode 表示最后一个子节点
     * @return
     */
    @Override
    public String reslultSure(WheelNode lastNode) {
        JSONObject jsonObject = treeTraversal(lastNode,null);
        return jsonObject.toString();
    }


    private JSONObject  treeTraversal(WheelNode node, JSONObject jsonObject){

        if (jsonObject == null) jsonObject = new JSONObject();
        if (node == null) return jsonObject;

        Object obj = node.mObject;

        if(obj != null && obj instanceof Province){
            Province province = (Province) node.mObject;
            try {
                JSONObject provinceObj = new JSONObject();
                provinceObj.put("id",province.provinceId);
                provinceObj.put("name",province.provinceName);
                provinceObj.put("code",province.provinceCode);
                jsonObject.put("province",provinceObj);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }


        if(obj != null && obj instanceof City){
            City city = (City) node.mObject;
            try {
                JSONObject cityObj = new JSONObject();
                cityObj.put("id",city.cityId);
                cityObj.put("name",city.cityName);
                cityObj.put("code",city.cityCode);
                jsonObject.put("city",cityObj);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        if(obj != null && obj instanceof County){
            County county = (County) node.mObject;
            try {
                JSONObject countyObj = new JSONObject();
                countyObj.put("id",county.countyId);
                countyObj.put("name",county.countyName);
                countyObj.put("code",county.countyCode);
                jsonObject.put("city",countyObj);
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        WheelNode parentNode =  node.getParentNode();
        return treeTraversal(parentNode,jsonObject);
    }

    private boolean isFindNode(long id,long findId,String name,String findName,String code,String findCode) {

        if (id > 0 && findId > 0 && id == findId){
            return true;
        }

        if (!isEmpty(name) && !isEmpty(findName) && name.equals(findName)){
            return true;
        }

        if (!isEmpty(code) && !isEmpty(findCode) && code.equals(findCode)){
            return true;
        }

        return false;
    }

    private boolean isEmpty(String msg){
        return "".equals(msg) || msg == null;
    }


    public void findNodePositionByProvince(long id,String name,String code, IndexCallBack callBack) {
        Province province = new Province();
        province.provinceId = id;
        province.provinceName = name;
        province.provinceCode=code;
        super.findNodePosition(new WheelNode(province),callBack);
    }

    public void findNodePositionByCity(long id,String name,String code, IndexCallBack callBack) {
        City city = new City();
        city.cityId = id;
        city.cityName = name;
        city.cityCode=code;
        super.findNodePosition(new WheelNode(city),callBack);
    }

    public void findNodePositionByCounty(long id,String name,String code, IndexCallBack callBack) {
        County county = new County();
        county.countyId = id;
        county.countyName = name;
        county.countyCode=code;
        super.findNodePosition(new WheelNode(county),callBack);
    }

}
